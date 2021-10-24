package io.fabric8.demo;

import io.fabric8.certmanager.api.model.v1.CertificateRequest;
import io.fabric8.certmanager.api.model.v1.CertificateRequestList;
import io.fabric8.certmanager.client.CertManagerClient;
import io.fabric8.certmanager.client.DefaultCertManagerClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateRequestWatchExample {
  private static final Logger logger = Logger.getLogger(CertificateRequestWatchExample.class.getSimpleName());

  public static void main(String[] args) {
    try (CertManagerClient certManagerClient = new DefaultCertManagerClient()) {
      Watch watch = certManagerClient.v1().certificateRequests()
        .inNamespace("default")
        .watch(new Watcher<>() {
          @Override
          public void eventReceived(Action action, CertificateRequest certificateRequest) {
            logger.info(String.format("%s %s", action.name(), certificateRequest.getMetadata().getName()));
          }

          @Override
          public void onClose(WatcherException e) {
            logger.info("Watch Closing: " + e.getMessage());
          }
        });
      logger.info("Watching CertificateRequests for 1 minute in default namespace");
      TimeUnit.MINUTES.sleep(1);
      watch.close();
    } catch (KubernetesClientException kubernetesClientException) {
      logger.log(Level.SEVERE, kubernetesClientException.getMessage());
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      logger.info("interrupted");
    }
  }
}
