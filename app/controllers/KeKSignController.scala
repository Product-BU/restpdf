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
package controllers

import com.typesafe.scalalogging.LazyLogging
import io.brainergy.pdf.kek.{KekSignFacade, KekSignForm}
import io.brainergy.util.BJson
import play.api.libs.Files
import play.api.mvc.{Action, MultipartFormData}

import javax.inject.{Inject, Singleton}

/**
 * @author Peerapat A on Feb 11, 2022
 */
@Singleton
class KeKSignController @Inject()(signer: KekSignFacade)
  extends BasedController
    with LazyLogging
    with BJson {

  def sign: Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData) { implicit request =>

    if (hasAuthorized())
      process(request.body.dataParts, Array.empty)
    else
      Unauthorized("Token Invalid")
  }

  private def process(data: Map[String, Seq[String]], pdfBytes: Array[Byte]) = try {
    val pdfB64 = if (data.contains("pdf_b64")) data("pdf_b64").head else ""
    val docClose = data("doc_close").head.toBoolean
    val docPassword = data("doc_password").head
    val masterAlias = data("master_alias").head
    val masterPassword = data("master_password").head
    val kek = data("kek").head
    val cert = data("cert").head

    val result = signer(KekSignForm(pdfB64, pdfBytes, docClose, docPassword, masterAlias, masterPassword, kek, cert))

    Ok(result.toByteArray).as("file/bin")
  } catch {
    case _: NullPointerException =>
      BadRequest
    case t: Throwable =>
      logger.warn(t.getMessage, t)
      InternalServerError
  }

}