/*  Copyright (C) 2017-2022 Andreas Shimokawa, Aniruddha Adhikary, Daniele
    Gobbetti, ivanovlev, kalaee, lazarosfs, McSym28, M. Hadi, Roi Greenberg,
    Taavi Eomäe, Ted Stein, Thomas, Yaron Shahrabani, José Rebelo

    This file is part of Gadgetbridge.

    Gadgetbridge is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Gadgetbridge is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */
package nodomain.freeyourgadget.gadgetbridge.util.language.impl;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.HashMap;

import nodomain.freeyourgadget.gadgetbridge.util.language.SimpleTransliterator;
import nodomain.freeyourgadget.gadgetbridge.util.language.Transliterator;

public class FlattenToAsciiTransliterator implements Transliterator {
    @Override
    public String transliterate(String txt) {
        if (txt == null || txt.isEmpty()) {
            return txt;
        }

        txt = Normalizer.normalize(txt, Normalizer.Form.NFD);
        txt = new String(txt.getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII);
        return txt.replaceAll("\\p{M}", "");
    }
}
