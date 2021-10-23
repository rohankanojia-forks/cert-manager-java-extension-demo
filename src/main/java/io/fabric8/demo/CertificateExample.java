package io.fabric8.demo;

import io.fabric8.certmanager.api.model.v1.Certificate;
import io.fabric8.certmanager.api.model.v1.CertificateBuilder;
import io.fabric8.certmanager.client.CertManagerClient;
import io.fabric8.certmanager.client.DefaultCertManagerClient;

public class CertificateExample {
    public static void main(String[] args) {
        try (CertManagerClient certManagerClient = new DefaultCertManagerClient()) {
            Certificate certificate = new CertificateBuilder()
                    .withNewMetadata().withName("acme-cert").endMetadata()
                    .withNewSpec()
                       .withSecretName("acme-crt-secret")
                       .addToDnsNames("foo.example.com", "bar.example.com")
                    .withNewIssuerRef("letsencrypt-pod", "Issuer", "cert-manager.io")
                    .endSpec()
                    .build();

            certManagerClient.v1().certificates().inNamespace("default").create(certificate);
        }
    }
}
