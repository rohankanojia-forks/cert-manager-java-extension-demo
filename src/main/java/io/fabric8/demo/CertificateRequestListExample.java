package io.fabric8.demo;

import io.fabric8.certmanager.api.model.v1.CertificateRequestList;
import io.fabric8.certmanager.client.CertManagerClient;
import io.fabric8.certmanager.client.DefaultCertManagerClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateRequestListExample {
  private static final Logger logger = Logger.getLogger(CertificateRequestListExample.class.getSimpleName());

  public static void main(String[] args) {
    try (CertManagerClient certManagerClient = new DefaultCertManagerClient()) {
       CertificateRequestList certificateRequestList = certManagerClient.v1().certificateRequests()
        .inNamespace("default")
        .list();
       logger.info(String.format("Found %d CertificateRequests in %s namespace.", certificateRequestList.getItems().size(), "default"));

    } catch (KubernetesClientException kubernetesClientException) {
      logger.log(Level.SEVERE, kubernetesClientException.getMessage());
    }
  }
}
