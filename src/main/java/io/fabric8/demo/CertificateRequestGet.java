package io.fabric8.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fabric8.certmanager.api.model.v1.CertificateRequest;
import io.fabric8.certmanager.client.CertManagerClient;
import io.fabric8.certmanager.client.DefaultCertManagerClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.internal.SerializationUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateRequestGet {
  private static final Logger logger = Logger.getLogger(CertificateRequestGet.class.getSimpleName());

  public static void main(String[] args) {
    try (CertManagerClient certManagerClient = new DefaultCertManagerClient()) {
      CertificateRequest certificateRequest = certManagerClient.v1()
        .certificateRequests()
        .inNamespace("default")
        .withName("my-ca-cr")
        .get();

      logger.info(String.format("%s found in default namespace", certificateRequest.getMetadata().getName()));
      logger.info(SerializationUtils.dumpAsYaml(certificateRequest));
    } catch (JsonProcessingException | KubernetesClientException exception) {
      logger.log(Level.SEVERE, exception.getMessage());
    }
  }
}
