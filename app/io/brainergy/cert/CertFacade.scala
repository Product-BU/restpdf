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
package io.brainergy.cert


import io.brainergy.pdf.sign.RestCertificate
import io.brainergy.util.Conf

import java.io._
import java.nio.file.{Files, Paths}
import java.security.cert.{CertificateFactory, X509Certificate}
import javax.inject.{Inject, Singleton}

@Singleton
class CertFacade @Inject()(val restCert: RestCertificate) {

  def store(certAlias: String): Unit = {
    val fileName = buildPath(certAlias)
    if (Files.exists(Paths.get(fileName)))
      throw new IllegalStateException("Cert Exist")

    val certBin = restCert.fetch(certAlias)
    try {
      buildX509(certBin)
    } catch {
      case _: Exception => throw new IllegalStateException("Cert Error")
    }

    Files.write(Paths.get(fileName), certBin)
  }

  def get(certAlias: String): Array[Byte] = loadCert(certAlias)

  def extract(cert: Array[Byte]): X509Certificate = {
    buildX509(cert)
  }

  def loadCert(certAlias: String): Array[Byte] = {
    val fileName = buildPath(certAlias)
    if (Files.exists(Paths.get(fileName)))
      Files.readAllBytes(Paths.get(buildPath(certAlias)))
    else
      restCert.fetch(certAlias)
  }

  def buildX509(certData: Array[Byte]): X509Certificate = CertificateFactory
    .getInstance("X509")
    .generateCertificate(new ByteArrayInputStream(certData))
    .asInstanceOf[X509Certificate]

  private def buildPath(certAlias: String) = s"$CERTS_DIRECTORY/$certAlias"

  private lazy val CERTS_DIRECTORY = Conf("certs.directory", "/app/resources")

}
