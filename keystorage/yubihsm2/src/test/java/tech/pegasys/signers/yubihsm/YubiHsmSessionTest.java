/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.signers.yubihsm;

import tech.pegasys.signers.yubihsm.backend.YubiHsmBackend;
import tech.pegasys.signers.yubihsm.backend.YubihsmConnectorBackend;
import tech.pegasys.signers.yubihsm.model.Opaque;

import java.net.URI;
import java.time.Duration;

import org.apache.tuweni.bytes.Bytes;
import org.junit.jupiter.api.Test;

class YubiHsmSessionTest {

  @Test
  void authenticateSessionAndGetOpaqueData() {
    final YubiHsmBackend backend =
        new YubihsmConnectorBackend(URI.create("http://localhost:12345"), Duration.ofSeconds(3));
    try (final YubiHsmSession yubiHsmSession =
        new YubiHsmSession(backend, (short) 1, "password".toCharArray())) {
      yubiHsmSession.authenticateSession();
      final short opaqueId = (short) 15;
      final Opaque opaque = new Opaque(opaqueId);
      final Bytes key1 = opaque.getOpaque(yubiHsmSession, Opaque.OutputFormat.HEX);
      System.out.println("Key in hex " + key1);
    }
  }
}
