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


import java.io.ByteArrayOutputStream

import com.itextpdf.kernel.pdf._
import javax.inject.Singleton


@Singleton
class PDFPassword {

  def apply(src: PdfReader, encryptwith: Array[Byte]): ByteArrayOutputStream = {
    val props: WriterProperties = new WriterProperties()
      .setStandardEncryption(encryptwith
        , encryptwith
        , EncryptionConstants.ALLOW_PRINTING
        , EncryptionConstants.ENCRYPTION_AES_256)

    val result = new ByteArrayOutputStream()
    val newPdf: PdfWriter = new PdfWriter(result, props)

    val pdfDoc = new PdfDocument(src, newPdf)
    pdfDoc.close()

    result
  }

}
