package io.fabric8.demo;

import io.fabric8.certmanager.api.model.v1.Issuer;
import io.fabric8.certmanager.api.model.v1.IssuerBuilder;
import io.fabric8.certmanager.client.CertManagerClient;
import io.fabric8.certmanager.client.DefaultCertManagerClient;

public class IssuerExample {
    public static void main(String[] args) {
        try (CertManagerClient certManagerClient = new DefaultCertManagerClient()) {
            Issuer issuer = new IssuerBuilder()
                    .withNewMetadata().withName("ca-issuer").endMetadata()
                    .withNewSpec()
                       .withNewCa()
                          .withSecretName("ca-key-pair")
                       .endCa()
                    .endSpec()
                    .build();

            certManagerClient.v1().issuers().inNamespace("default").create(issuer);
        }
    }
}
