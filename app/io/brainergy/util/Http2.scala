/*
 * This file is part of Brainergy RestPDF.
 *
 * Brainergy RestPDF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Brainergy RestPDF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package io.brainergy.util

import com.typesafe.scalalogging.LazyLogging

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets
import java.time.Duration


case class Http2(timeout: Int, urls: List[String]) extends LazyLogging {

  private val uris = urls.map(u => URI.create(u))

  private val client = HttpClient.newBuilder
    .version(HttpClient.Version.HTTP_2)
    .connectTimeout(Duration.ofSeconds(timeout))
    .build

  def get(headers: Map[String, String]): HttpResponse[String] = {
    val builder = HttpRequest.newBuilder
    headers.foreach(kv => builder.header(kv._1, kv._2))

    for (uri <- uris) {
      try {
        val request: HttpRequest = builder.GET.uri(uri).build
        return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
      } catch {
        case t: Throwable =>
          logger.error(t.getMessage, t)
          logger.warn(t.getMessage + " : " + uri)
      }
    }

    throw new IllegalStateException("error")
  }

  def post(headers: Map[String, String], body: String): HttpResponse[String] = {
    val builder = HttpRequest.newBuilder
    headers.foreach(kv => builder.header(kv._1, kv._2))

    for (uri <- uris) {
      try {
        val request: HttpRequest = builder
          .POST(HttpRequest.BodyPublishers.ofString(body))
          .uri(uri).build
        return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
      } catch {
        case t: Throwable =>
          logger.warn(t.getMessage + " : " + uri)
      }
    }

    throw new IllegalStateException("error")
  }

}
