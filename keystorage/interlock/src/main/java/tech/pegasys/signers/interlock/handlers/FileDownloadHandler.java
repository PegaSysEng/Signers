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
package tech.pegasys.signers.interlock.handlers;

import java.nio.charset.StandardCharsets;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonObject;

public class FileDownloadHandler extends AbstractHandler<String> {

  public FileDownloadHandler() {
    super("File Download");
  }

  @Override
  protected String processJsonResponse(final JsonObject json, final MultiMap headers) {
    return null;
  }

  @Override
  public String body() {
    return null;
  }

  @Override
  protected void handleResponseBuffer(final HttpClientResponse response, final Buffer buffer) {
    getResponseFuture().complete(buffer.toString(StandardCharsets.UTF_8));
  }
}
