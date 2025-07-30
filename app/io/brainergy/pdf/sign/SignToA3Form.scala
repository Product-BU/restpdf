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

import java.io.{ByteArrayInputStream, InputStream}
import java.util.Base64

import com.fasterxml.jackson.annotation.JsonIgnore

/**
{
    "pdffile_b64": "... ",
    "doc_closed": true,
    "doc_password": "",
    "signature": [
      {
          "position_x": "334.5548549810845"
          , "position_y": "874.9772727272727"
          , "page_number":"1"
          , "dimension_width": "134.5701904296875"
          , "dimension_height": "78.038330078125"
          , "cert_alias": "XXXX_CERT"
          , "key_alias": "XXXX_KEY"
          , "img": "..."
      }
    ]
}
*/
case class SignToA3Form(pdfB64: String
                        , docClosed: Boolean
                        , docPassword: String
                        , keyAlias: String
                        , certAlias: String) {

  @JsonIgnore
  val pdfStream: InputStream = new ByteArrayInputStream(Base64.getDecoder.decode(pdfB64))

}
