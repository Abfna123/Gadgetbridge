/*  Copyright (C) 2016-2020 Andreas Shimokawa, Carsten Pfeiffer, Daniele
    Gobbetti

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

package nodomain.freeyourgadget.gadgetbridge.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

// FIXME: document me and my fields, including units
public class WeatherSpec implements Parcelable, Serializable {

    public static final Creator<WeatherSpec> CREATOR = new Creator<WeatherSpec>() {
        @Override
        public WeatherSpec createFromParcel(Parcel in) {
            return new WeatherSpec(in);
        }

        @Override
        public WeatherSpec[] newArray(int size) {
            return new WeatherSpec[size];
        }
    };
    public static final int VERSION = 3;
    private static final long serialVersionUID = VERSION;
    public int timestamp;
    public String location;
    public int currentTemp; // kelvin
    public int currentConditionCode = 3200;
    public String currentCondition;
    public int currentHumidity;
    public int todayMaxTemp; // kelvin
    public int todayMinTemp; // kelvin
    public float windSpeed; // km per hour
    public int windDirection; // deg
    public float uvIndex;
    public int precipProbability; // %

    public ArrayList<Forecast> forecasts = new ArrayList<>();

    public WeatherSpec() {

    }

    // Lower bounds of beaufort regions 1 to 12
    // Values from https://en.wikipedia.org/wiki/Beaufort_scale
    static final float[] beaufort = new float[] { 2, 6, 12, 20, 29, 39, 50, 62, 75, 89, 103, 118 };
    //                                    level: 0 1  2   3   4   5   6   7   8   9   10   11   12

    public int windSpeedAsBeaufort() {
        int l = 0;
        while (l < beaufort.length && beaufort[l] < this.windSpeed) {
            l++;
        }
        return l;
    }

    protected WeatherSpec(Parcel in) {
        int version = in.readInt();
        if (version >= 2) {
            timestamp = in.readInt();
            location = in.readString();
            currentTemp = in.readInt();
            currentConditionCode = in.readInt();
            currentCondition = in.readString();
            currentHumidity = in.readInt();
            todayMaxTemp = in.readInt();
            todayMinTemp = in.readInt();
            windSpeed = in.readFloat();
            windDirection = in.readInt();
            in.readList(forecasts, Forecast.class.getClassLoader());
        }
        if (version >= 3) {
            uvIndex = in.readFloat();
            precipProbability = in.readInt();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(VERSION);
        dest.writeInt(timestamp);
        dest.writeString(location);
        dest.writeInt(currentTemp);
        dest.writeInt(currentConditionCode);
        dest.writeString(currentCondition);
        dest.writeInt(currentHumidity);
        dest.writeInt(todayMaxTemp);
        dest.writeInt(todayMinTemp);
        dest.writeFloat(windSpeed);
        dest.writeInt(windDirection);
        dest.writeList(forecasts);
        dest.writeFloat(uvIndex);
        dest.writeInt(precipProbability);
    }

    public static class Forecast implements Parcelable, Serializable {
        private static final long serialVersionUID = 1L;

        public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
            @Override
            public Forecast createFromParcel(Parcel in) {
                return new Forecast(in);
            }

            @Override
            public Forecast[] newArray(int size) {
                return new Forecast[size];
            }
        };
        public int minTemp;
        public int maxTemp;
        public int conditionCode;
        public int humidity;

        public Forecast() {
        }

        public Forecast(int minTemp, int maxTemp, int conditionCode, int humidity) {
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            this.conditionCode = conditionCode;
            this.humidity = humidity;
        }

        Forecast(Parcel in) {
            minTemp = in.readInt();
            maxTemp = in.readInt();
            conditionCode = in.readInt();
            humidity = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(minTemp);
            dest.writeInt(maxTemp);
            dest.writeInt(conditionCode);
            dest.writeInt(humidity);
        }
    }
}
