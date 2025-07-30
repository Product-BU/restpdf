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
package io.brainergy.pdf.sign


import com.typesafe.scalalogging.LazyLogging
import io.brainergy.util.{B64, Conf}
import scalaj.http.Http
import scalaj.http.HttpResponse

import javax.inject.Singleton
import scala.collection.mutable
import scala.concurrent.duration.{Duration, _}
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

@Singleton
class RestCertificate extends LazyLogging with B64 {

  private var activeURL = 0
  private val buffer = mutable.Map[String, Array[Byte]]()

  def fetch(certAlias: String): Array[Byte] = {
    if (buffer.contains(certAlias))
      return buffer(certAlias)

    retry(4) {
      val response = Http(s"${uri()}/x509/cert")
        .header("X-Access-Token", HSM_TOKEN)
        .postData(certAlias)
        .asString
      checkRes2xx(response)
      val cert = decoder.decode(response.body)
      if (!isEmpty(cert)) {
        buffer.put(certAlias, cert)
      }
      cert
    }
  }

  @annotation.tailrec
  final def retry[A](limit: Int
                     , count: Int = 1
                     , backoff: Duration = 5 seconds)(f: => A): A = Try(f) match {
    case Success(x) => x

    case Failure(e) =>
      logger.error(e.getMessage, e.getCause)
      activeURL = if (activeURL < HSM_URLS.size - 1) activeURL + 1 else 0
      if (count < limit) {
        Thread.sleep(backoff.toMillis)
        logger.info(
          s"Thread %d retry number %d, will fetch x509 with activeURL: %s".format(
            Thread.currentThread().getId,
            count,
            uri()
          )
        )
        retry(limit, count + 1, backoff)(f)
      } else {
        throw new RuntimeException(s"Reach Retry limit $count times", e)
      }
  }

  private def uri() = HSM_URLS(activeURL)

  private lazy val HSM_URLS = Conf("hsm.url").split(",")
  private lazy val HSM_TOKEN = Conf("hsm.token")

  private def isEmpty(input : Array[Byte]) : Boolean = {
    input == null || input.isEmpty
  }

  private def checkRes2xx(res :HttpResponse[String]) : Unit = {
    if (!res.isSuccess) {
      throw new RuntimeException(
        "Thread %d fetch x509 doesn't response with 2xx, got %d".format(
          Thread.currentThread().getId,
          res.code
        )
      )
    }
  }
}