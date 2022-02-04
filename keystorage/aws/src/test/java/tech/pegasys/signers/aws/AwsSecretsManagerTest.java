package tech.pegasys.signers.aws;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AwsSecretsManagerTest {

  private static final SecretsManagerClient SECRETS_MANAGER_CLIENT = AwsSecretsManager.createSecretsManagerClient(Region.US_EAST_2);
  private static final String SECRET_NAME = "arn:aws:secretsmanager:us-east-2:504983140689:secret:web3signer-testsecret-K7aIgv";
  private static final String SECRET_KEY = "keystore";
  private static final String EXPECTED_KEYSTORE = "{\"crypto\": {\"kdf\": {\"function\": \"scrypt\", \"params\": {\"dklen\": 32, \"n\": 262144, \"r\": 8, \"p\": 1, \"salt\": \"3d9b30b612f4f5e9423dc43c0490396798a179d35dd58d48dc1f5d6d42b07ab6\"}, \"message\": \"\"}, \"checksum\": {\"function\": \"sha256\", \"params\": {}, \"message\": \"c762b7453eab3332cda31d9dee1894cf541373617e591a8e7ab8f14f5830f723\"}, \"cipher\": {\"function\": \"aes-128-ctr\", \"params\": {\"iv\": \"095f79f6bb5daab60355ab6aa894b3c8\"}, \"message\": \"4ca342a769ec1c00d6a6d69e18cdf821f42849d4431da7df827b01ba162ed763\"}}, \"description\": \"\", \"pubkey\": \"8fb7c68f3291b8db46ef86a8b9544cad7052dd7cf817862063d1f151f3c443cd3907830b09a86fe0513f0e863beccf25\", \"path\": \"m/12381/3600/0/0/0\", \"uuid\": \"88fc9701-8670-4378-a3ba-00be25c1330c\", \"version\": 4}";

  @Test
  void retrieveExistingKeyStoreSecret(){
    String keystore = AwsSecretsManager.getKeyStoreValue(SECRETS_MANAGER_CLIENT, SECRET_NAME, SECRET_KEY);
    assertThat(keystore).isNotEmpty().isEqualTo(EXPECTED_KEYSTORE);
  }

  @Test
  void nonExistentKeyStoreSecretReturnsNull(){
    String keystore = AwsSecretsManager.getKeyStoreValue(SECRETS_MANAGER_CLIENT, SECRET_NAME, "nonexistent");
    assertThat(keystore).isNullOrEmpty();
  }

  @Test
  void nonExistentSecretThrowsException(){
    assertThatExceptionOfType(RuntimeException.class)
      .isThrownBy(() -> AwsSecretsManager.getKeyStoreValue(SECRETS_MANAGER_CLIENT, "empty", SECRET_KEY))
      .withMessageContaining("Secrets Manager can't find the specified secret.");
  }
}