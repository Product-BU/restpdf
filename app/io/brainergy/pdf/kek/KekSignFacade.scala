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
package io.brainergy.pdf.kek

import java.io._
import javax.inject.{Inject, Singleton}

@Singleton
class KekSignFacade @Inject()() extends KekSignMeta {

  def apply(form: KekSignForm): ByteArrayOutputStream = {
    val result = new ByteArrayOutputStream()
    val signer = buildSigner(form.pdfBytes, Option(form.docPassword), result)
    sign(signer =  signer
      , masterAlias = form.masterAlias
      , masterPassword = form.masterPassword
      , kekB64 = form.kekB64
      , certB64 = form.certB64
      , docClose = form.docClosed)

    result
  }

}
