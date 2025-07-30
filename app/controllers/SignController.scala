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
import io.brainergy.pdf.sign.{SignFacade, SignForm}
import io.brainergy.util.{BJson, Conf}
import play.api.libs.Files
import play.api.mvc.{Action, MultipartFormData}

import javax.inject.{Inject, Singleton}

/**
 * @author Peerapat A on Feb 11, 2022
 */
@Singleton
class SignController @Inject()(signer: SignFacade)
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
    val pdfB64 = if (data.contains("pdf_file")) data("pdf_file").head else ""
    val docClose = data("doc_close").head.toBoolean
    val docPassword = if (data("doc_password").head == "") null else data("doc_password").head
    val keyAlias = data("key_alias").head
    val certAlias = data("cert_alias").head

    val result = signer(SignForm(pdfB64, pdfBytes, docClose, docPassword, keyAlias, certAlias))

    Ok(result.toByteArray).as("file/bin")
  } catch {
    case _: NullPointerException =>

      BadRequest
    case t: Throwable =>
      logger.warn(t.getMessage, t)
      InternalServerError
  }

}
