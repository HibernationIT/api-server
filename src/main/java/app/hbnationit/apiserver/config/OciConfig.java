package app.hbnationit.apiserver.config;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.StringPrivateKeySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.function.Supplier;

@Configuration
public class OciConfig {
    public static String PRIVATE_KEY;
    public static String TENANT_ID;
    public static String USER_ID;
    public static String FINGERPRINT;
    public static String BUCKET_NAME;
    public static String NAMESPACE_NAME;

    @Value("${spring.oci.private-key}")
    public void setPrivateKey(String value) {
        PRIVATE_KEY = value;
    }
    @Value("${spring.oci.tenant-id}")
    public void setTenantId(String value) {
        TENANT_ID = value;
    }
    @Value("${spring.oci.user-id}")
    public void setUserId(String value) {
        USER_ID = value;
    }
    @Value("${spring.oci.fingerprint}")
    public void setFingerprint(String value) {
        FINGERPRINT = value;
    }
    @Value("${spring.oci.bucket}")
    public void setBucketName(String value) {
        BUCKET_NAME = value;
    }
    @Value("${spring.oci.namespace}")
    public void setNamespaceName(String value) {
        NAMESPACE_NAME = value;
    }

    @Bean
    public AuthenticationDetailsProvider simpleAuthenticationDetailsProvider() {
        Supplier<InputStream> privateKeySupplier = new StringPrivateKeySupplier(PRIVATE_KEY);
        return SimpleAuthenticationDetailsProvider.builder()
                .tenantId(TENANT_ID)
                .userId(USER_ID)
                .fingerprint(FINGERPRINT)
                .region(Region.AP_CHUNCHEON_1)
                .privateKeySupplier(privateKeySupplier)
                .build();
    }
}
