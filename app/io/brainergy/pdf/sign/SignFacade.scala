/*
 * RestPDF
 * Copyright (C) 2025 Brainergy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package io.brainergy.pdf.sign

import io.brainergy.cert.CertFacade

import java.io._
import javax.inject.{Inject, Singleton}

@Singleton
class SignFacade @Inject()(val certFacade: CertFacade) extends SignMeta {

  def apply(form: SignForm): ByteArrayOutputStream = {
    val result = new ByteArrayOutputStream()
    val signer = buildSigner(form.pdfBytes, Option(form.docPassword), result)
    sign(signer =  signer
      , keyAlias = form.keyAlias
      , certAlias = form.certAlias
      , docClose = form.docClosed)

    result
  }

}
