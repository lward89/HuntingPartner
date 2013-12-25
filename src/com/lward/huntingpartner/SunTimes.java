package com.lward.huntingpartner;

import java.util.Calendar;

public class SunTimes{
	
	//***********************************************************************/
	//***********************************************************************/
	//*												*/
	//*This section contains subroutines used in calculating solar position */
	//*												*/
	//***********************************************************************/
	//***********************************************************************/

	// Convert radian angle to degrees

		public static double radToDeg(double angleRad) 
		{
			return Math.toDegrees(angleRad);
		}

	//*********************************************************************/

	// Convert degree angle to radians

		public static double degToRad(double angleDeg) 
		{
			return Math.toRadians(angleDeg);
		}

	//*********************************************************************/


	//***********************************************************************/
	//* Name:    calcDayOfYear								*/
	//* Type:    Function									*/
	//* Purpose: Finds numerical day-of-year from mn, day and lp year info  */
	//* Arguments:										*/
	//*   month: January = 1								*/
	//*   day  : 1 - 31									*/
	//*   lpyr : 1 if leap year, 0 if not						*/
	//* Return value:										*/
	//*   The numerical day of year							*/
	//***********************************************************************/

		public static int calcDayOfYear(Calendar date){
			return date.get(Calendar.DAY_OF_YEAR);
		}


	//***********************************************************************/
	//* Name:    calcDayOfWeek								*/
	//* Type:    Function									*/
	//* Purpose: Derives weekday from Julian Day					*/
	//* Arguments:										*/
	//*   juld : Julian Day									*/
	//* Return value:										*/
	//*   String containing name of weekday						*/
	//***********************************************************************/

		public static String calcDayOfWeek(double juld)
		{
			double A = (juld + 1.5) % 7;
			String DOW = (A==0)?"Sunday":(A==1)?"Monday":(A==2)?"Tuesday":(A==3)?"Wednesday":(A==4)?"Thursday":(A==5)?"Friday":"Saturday";
			return DOW;
		}


	//***********************************************************************/
	//* Name:    calcJD									*/
	//* Type:    Function									*/
	//* Purpose: Julian day from calendar day						*/
	//* Arguments:										*/
	//*   year : 4 digit year								*/
	//*   month: January = 1								*/
	//*   day  : 1 - 31									*/
	//* Return value:										*/
	//*   The Julian day corresponding to the date					*/
	//* Note:											*/
	//*   Number is returned for start of day.  Fractional days should be	*/
	//*   added later.									*/
	//***********************************************************************/

		public static double calcJD(int year,int month,int day)
		{
			if (month <= 2) {
				year -= 1;
				month += 12;
			}
			double A = Math.floor(year/100);
			double B = 2 - A + Math.floor(A/4);

			double JD = Math.floor(365.25*(year + 4716)) + Math.floor(30.6001*(month+1)) + day + B - 1524.5;
			return JD;
		}



	//***********************************************************************/
	//* Name:    calcDateFromJD								*/
	//* Type:    Function									*/
	//* Purpose: Calendar date from Julian Day					*/
	//* Arguments:										*/
	//*   jd   : Julian Day									*/
	//* Return value:										*/
	//*   String date in the form DD-MONTHNAME-YYYY					*/
	//* Note:											*/
	//***********************************************************************/

		public static Calendar calcDateFromJD(double jd)
		{
			double z = Math.floor(jd + 0.5);
			double f = (jd + 0.5) - z;
			
			double A;
			if (z < 2299161) {
				A = z;
			} else {
				double alpha = Math.floor((z - 1867216.25)/36524.25);
				A = z + 1 + alpha - Math.floor(alpha/4);
			}

			double B = A + 1524;
			double C = Math.floor((B - 122.1)/365.25);
			double D = Math.floor(365.25 * C);
			double E = Math.floor((B - D)/30.6001);

			double day = B - D - Math.floor(30.6001 * E) + f;
			double month = (E < 14) ? E - 1 : E - 13;
			double year = (month > 2) ? C - 4716 : C - 4715;
			
			Calendar calDate = Calendar.getInstance();
			calDate.set((int)year, (int)month, (int)day);
			// alert ("date: " + day + "-" + monthList[month-1].name + "-" + year);
			return calDate;
		}


	//***********************************************************************/
	//* Name:    calcDayFromJD								*/
	//* Type:    Function									*/
	//* Purpose: Calendar day (minus year) from Julian Day			*/
	//* Arguments:										*/
	//*   jd   : Julian Day									*/
	//* Return value:										*/
	//*   String date in the form DD-MONTH						*/
	//***********************************************************************/

		public static Calendar calcDayFromJD(double jd)
		{
			double z = Math.floor(jd + 0.5);
			double f = (jd + 0.5) - z;
			
			double A;
			if (z < 2299161) {
				A = z;
			} else {
				double alpha = Math.floor((z - 1867216.25)/36524.25);
				A = z + 1 + alpha - Math.floor(alpha/4);
			}

			double B = A + 1524;
			double C = Math.floor((B - 122.1)/365.25);
			double D = Math.floor(365.25 * C);
			double E = Math.floor((B - D)/30.6001);

			double day = B - D - Math.floor(30.6001 * E) + f;
			double month = (E < 14) ? E - 1 : E - 13;
			double year = (month > 2) ? C - 4716 : C - 4715;
			
			Calendar calcDate = Calendar.getInstance();
			calcDate.set((int)year, (int)month, (int)day);

			return calcDate;
		}


	//***********************************************************************/
	//* Name:    calcTimeJulianCent							*/
	//* Type:    Function									*/
	//* Purpose: convert Julian Day to centuries since J2000.0.			*/
	//* Arguments:										*/
	//*   jd : the Julian Day to convert						*/
	//* Return value:										*/
	//*   the T value corresponding to the Julian Day				*/
	//***********************************************************************/

		public static double calcTimeJulianCent(double jd)
		{
			double T = (jd - 2451545.0)/36525.0;
			return T;
		}


	//***********************************************************************/
	//* Name:    calcJDFromJulianCent							*/
	//* Type:    Function									*/
	//* Purpose: convert centuries since J2000.0 to Julian Day.			*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   the Julian Day corresponding to the t value				*/
	//***********************************************************************/

		public static double calcJDFromJulianCent(double t)
		{
			double JD = t * 36525.0 + 2451545.0;
			return JD;
		}


	//***********************************************************************/
	//* Name:    calGeomMeanLongSun							*/
	//* Type:    Function									*/
	//* Purpose: calculate the Geometric Mean Longitude of the Sun		*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   the Geometric Mean Longitude of the Sun in degrees			*/
	//***********************************************************************/

		public static double calcGeomMeanLongSun(double t)
		{
			double LTheta = 280.46646 + t * (36000.76983 + 0.0003032 * t);
			while(LTheta > 360.0)
			{
				LTheta -= 360.0;
			}
			while(LTheta < 0.0)
			{
				LTheta += 360.0;
			}
			return LTheta;		// in degrees
		}


	//***********************************************************************/
	//* Name:    calGeomAnomalySun							*/
	//* Type:    Function									*/
	//* Purpose: calculate the Geometric Mean Anomaly of the Sun		*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   the Geometric Mean Anomaly of the Sun in degrees			*/
	//***********************************************************************/

		public static double calcGeomMeanAnomalySun(double t)
		{
			double M = 357.52911 + t * (35999.05029 - 0.0001537 * t);
			return M;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcEccentricityEarthOrbit						*/
	//* Type:    Function									*/
	//* Purpose: calculate the eccentricity of earth's orbit			*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   the unitless eccentricity							*/
	//***********************************************************************/


		public static double calcEccentricityEarthOrbit(double t)
		{
			double e = 0.016708634 - t * (0.000042037 + 0.0000001267 * t);
			return e;		// unitless
		}

	//***********************************************************************/
	//* Name:    calcSunEqOfCenter							*/
	//* Type:    Function									*/
	//* Purpose: calculate the equation of center for the sun			*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   in degrees										*/
	//***********************************************************************/


		public static double calcSunEqOfCenter(double t)
		{
			double m = calcGeomMeanAnomalySun(t);

			double mrad = degToRad(m);
			double sinm = Math.sin(mrad);
			double sin2m = Math.sin(mrad+mrad);
			double sin3m = Math.sin(mrad+mrad+mrad);

			double C = sinm * (1.914602 - t * (0.004817 + 0.000014 * t)) + sin2m * (0.019993 - 0.000101 * t) + sin3m * 0.000289;
			return C;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcSunTrueLong								*/
	//* Type:    Function									*/
	//* Purpose: calculate the true longitude of the sun				*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   sun's true longitude in degrees						*/
	//***********************************************************************/


		public static double calcSunTrueLong(double t)
		{
			double lTheta = calcGeomMeanLongSun(t);
			double c = calcSunEqOfCenter(t);

			double O = lTheta + c;
			return O;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcSunTrueAnomaly							*/
	//* Type:    Function									*/
	//* Purpose: calculate the true anamoly of the sun				*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   sun's true anamoly in degrees							*/
	//***********************************************************************/

		public static double calcSunTrueAnomaly(double t)
		{
			double m = calcGeomMeanAnomalySun(t);
			double c = calcSunEqOfCenter(t);

			double v = m + c;
			return v;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcSunRadVector								*/
	//* Type:    Function									*/
	//* Purpose: calculate the distance to the sun in AU				*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   sun radius vector in AUs							*/
	//***********************************************************************/

		public static double calcSunRadVector(double t)
		{
			double v = calcSunTrueAnomaly(t);
			double e = calcEccentricityEarthOrbit(t);
	 
			double R = (1.000001018 * (1 - e * e)) / (1 + e * Math.cos(degToRad(v)));
			return R;		// in AUs
		}

	//***********************************************************************/
	//* Name:    calcSunApparentLong							*/
	//* Type:    Function									*/
	//* Purpose: calculate the apparent longitude of the sun			*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   sun's apparent longitude in degrees						*/
	//***********************************************************************/

		public static double calcSunApparentLong(double t)
		{
			double o = calcSunTrueLong(t);

			double omega = 125.04 - 1934.136 * t;
			double lambda = o - 0.00569 - 0.00478 * Math.sin(degToRad(omega));
			return lambda;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcMeanObliquityOfEcliptic						*/
	//* Type:    Function									*/
	//* Purpose: calculate the mean obliquity of the ecliptic			*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   mean obliquity in degrees							*/
	//***********************************************************************/

		public static double calcMeanObliquityOfEcliptic(double t)
		{
			double seconds = 21.448 - t*(46.8150 + t*(0.00059 - t*(0.001813)));
			double eTheta = 23.0 + (26.0 + (seconds/60.0))/60.0;
			return eTheta;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcObliquityCorrection						*/
	//* Type:    Function									*/
	//* Purpose: calculate the corrected obliquity of the ecliptic		*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   corrected obliquity in degrees						*/
	//***********************************************************************/

		public static double calcObliquityCorrection(double t)
		{
			double eTheta = calcMeanObliquityOfEcliptic(t);

			double omega = 125.04 - 1934.136 * t;
			double e = eTheta + 0.00256 * Math.cos(degToRad(omega));
			return e;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcSunRtAscension							*/
	//* Type:    Function									*/
	//* Purpose: calculate the right ascension of the sun				*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   sun's right ascension in degrees						*/
	//***********************************************************************/

		public static double calcSunRtAscension(double t)
		{
			double e = calcObliquityCorrection(t);
			double lambda = calcSunApparentLong(t);
	 
			double tananum = (Math.cos(degToRad(e)) * Math.sin(degToRad(lambda)));
			double tanadenom = (Math.cos(degToRad(lambda)));
			double alpha = radToDeg(Math.atan2(tananum, tanadenom));
			return alpha;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcSunDeclination							*/
	//* Type:    Function									*/
	//* Purpose: calculate the declination of the sun				*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   sun's declination in degrees							*/
	//***********************************************************************/

		public static double calcSunDeclination(double t)
		{
			double e = calcObliquityCorrection(t);
			double lambda = calcSunApparentLong(t);

			double sint = Math.sin(degToRad(e)) * Math.sin(degToRad(lambda));
			double theta = radToDeg(Math.asin(sint));
			return theta;		// in degrees
		}

	//***********************************************************************/
	//* Name:    calcEquationOfTime							*/
	//* Type:    Function									*/
	//* Purpose: calculate the difference between true solar time and mean	*/
	//*		solar time									*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//* Return value:										*/
	//*   equation of time in minutes of time						*/
	//***********************************************************************/

		public static double calcEquationOfTime(double t)
		{
			double epsilon = calcObliquityCorrection(t);
			double q = calcGeomMeanLongSun(t);
			double e = calcEccentricityEarthOrbit(t);
			double m = calcGeomMeanAnomalySun(t);

			double y = Math.tan(degToRad(epsilon)/2.0);
			y = y*y;

			double sin2q = Math.sin(2.0 * degToRad(q));
			double sinm   = Math.sin(degToRad(m));
			double cos2q = Math.cos(2.0 * degToRad(q));
			double sin4q = Math.sin(4.0 * degToRad(q));
			double sin2m  = Math.sin(2.0 * degToRad(m));

			double Etime = y * sin2q - 2.0 * e * sinm + 4.0 * e * y * sinm * cos2q
					- 0.5 * y * y * sin4q - 1.25 * e * e * sin2m;

			return radToDeg(Etime)*4.0;	// in minutes of time
		}

	//***********************************************************************/
	//* Name:    calcHourAngleSunrise							*/
	//* Type:    Function									*/
	//* Purpose: calculate the hour angle of the sun at sunrise for the	*/
	//*			latitude								*/
	//* Arguments:										*/
	//*   lat : latitude of observer in degrees					*/
	//*	solarDec : declination angle of sun in degrees				*/
	//* Return value:										*/
	//*   hour angle of sunrise in radians						*/
	//***********************************************************************/

		public static double calcHourAngleSunrise(double lat, double solarDec)
		{
			double latRad = degToRad(lat);
			double sdRad  = degToRad(solarDec);

			double HAarg = (Math.cos(degToRad(90.833))/(Math.cos(latRad)*Math.cos(sdRad))-Math.tan(latRad) * Math.tan(sdRad));

			double HA = (Math.acos(Math.cos(degToRad(90.833))/(Math.cos(latRad)*Math.cos(sdRad))-Math.tan(latRad) * Math.tan(sdRad)));

			return HA;		// in radians
		}

	//***********************************************************************/
	//* Name:    calcHourAngleSunset							*/
	//* Type:    Function									*/
	//* Purpose: calculate the hour angle of the sun at sunset for the	*/
	//*			latitude								*/
	//* Arguments:										*/
	//*   lat : latitude of observer in degrees					*/
	//*	solarDec : declination angle of sun in degrees				*/
	//* Return value:										*/
	//*   hour angle of sunset in radians						*/
	//***********************************************************************/

		public static double calcHourAngleSunset(double lat, double solarDec)
		{
			double latRad = degToRad(lat);
			double sdRad  = degToRad(solarDec);

			double HAarg = (Math.cos(degToRad(90.833))/(Math.cos(latRad)*Math.cos(sdRad))-Math.tan(latRad) * Math.tan(sdRad));

			double HA = (Math.acos(Math.cos(degToRad(90.833))/(Math.cos(latRad)*Math.cos(sdRad))-Math.tan(latRad) * Math.tan(sdRad)));

			return -HA;		// in radians
		}


	//***********************************************************************/
	//* Name:    calcSunriseUTC								*/
	//* Type:    Function									*/
	//* Purpose: calculate the Universal Coordinated Time (UTC) of sunrise	*/
	//*			for the given day at the given location on earth	*/
	//* Arguments:										*/
	//*   JD  : julian day									*/
	//*   latitude : latitude of observer in degrees				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   time in minutes from zero Z							*/
	//***********************************************************************/

		public static double calcSunriseUTC(double JD, double latitude, double longitude)
		{
			double t = calcTimeJulianCent(JD);

			// *** Find the time of solar noon at the location, and use
	        //     that declination. This is better than start of the 
	        //     Julian day

			double noonmin = calcSolNoonUTC(t, longitude);
			double tnoon = calcTimeJulianCent (JD+noonmin/1440.0);

			// *** First pass to approximate sunrise (using solar noon)

			double eqTime = calcEquationOfTime(tnoon);
			double solarDec = calcSunDeclination(tnoon);
			double hourAngle = calcHourAngleSunrise(latitude, solarDec);

			double delta = longitude - radToDeg(hourAngle);
			double timeDiff = 4 * delta;	// in minutes of time
			double timeUTC = 720 + timeDiff - eqTime;	// in minutes

			// System.out.println("eqTime = " + eqTime + "\nsolarDec = " + solarDec + "\ntimeUTC = " + timeUTC);

			// *** Second pass includes fractional jday in gamma calc

			double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC/1440.0); 
			eqTime = calcEquationOfTime(newt);
			solarDec = calcSunDeclination(newt);
			hourAngle = calcHourAngleSunrise(latitude, solarDec);
			delta = longitude - radToDeg(hourAngle);
			timeDiff = 4 * delta;
			timeUTC = 720 + timeDiff - eqTime; // in minutes

			// System.out.println("eqTime = " + eqTime + "\nsolarDec = " + solarDec + "\ntimeUTC = " + timeUTC);

			return timeUTC;
		}

	//***********************************************************************/
	//* Name:    calcSolNoonUTC								*/
	//* Type:    Function									*/
	//* Purpose: calculate the Universal Coordinated Time (UTC) of solar	*/
	//*		noon for the given day at the given location on earth		*/
	//* Arguments:										*/
	//*   t : number of Julian centuries since J2000.0				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   time in minutes from zero Z							*/
	//***********************************************************************/

		public static double calcSolNoonUTC(double t, double longitude)
		{
			// First pass uses approximate solar noon to calculate eqtime
			double tnoon = calcTimeJulianCent(calcJDFromJulianCent(t) + longitude/360.0);
			double eqTime = calcEquationOfTime(tnoon);
			double solNoonUTC = 720 + (longitude * 4) - eqTime; // min

			double newt = calcTimeJulianCent(calcJDFromJulianCent(t) -0.5 + solNoonUTC/1440.0); 

			eqTime = calcEquationOfTime(newt);
			// var solarNoonDec = calcSunDeclination(newt);
			solNoonUTC = 720 + (longitude * 4) - eqTime; // min
			
			return solNoonUTC;
		}

	//***********************************************************************/
	//* Name:    calcSunsetUTC								*/
	//* Type:    Function									*/
	//* Purpose: calculate the Universal Coordinated Time (UTC) of sunset	*/
	//*			for the given day at the given location on earth	*/
	//* Arguments:										*/
	//*   JD  : julian day									*/
	//*   latitude : latitude of observer in degrees				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   time in minutes from zero Z							*/
	//***********************************************************************/

		public static double calcSunsetUTC(double JD, double latitude, double longitude)
		{
			double t = calcTimeJulianCent(JD);

			// *** Find the time of solar noon at the location, and use
	        //     that declination. This is better than start of the 
	        //     Julian day

			double noonmin = calcSolNoonUTC(t, longitude);
			double tnoon = calcTimeJulianCent (JD+noonmin/1440.0);

			// First calculates sunrise and approx length of day

			double eqTime = calcEquationOfTime(tnoon);
			double solarDec = calcSunDeclination(tnoon);
			double hourAngle = calcHourAngleSunset(latitude, solarDec);

			double delta = longitude - radToDeg(hourAngle);
			double timeDiff = 4 * delta;
			double timeUTC = 720 + timeDiff - eqTime;

			// first pass used to include fractional day in gamma calc

			double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC/1440.0); 
			eqTime = calcEquationOfTime(newt);
			solarDec = calcSunDeclination(newt);
			hourAngle = calcHourAngleSunset(latitude, solarDec);

			delta = longitude - radToDeg(hourAngle);
			timeDiff = 4 * delta;
			timeUTC = 720 + timeDiff - eqTime; // in minutes

			return timeUTC;
		}


	//*********************************************************************/

	// Returns the decimal latitude from the degrees, minutes and seconds entered 
	// into the form	

		public static double getLatitude()
		{
			double decLat = Hunting.latitude;
			
			return decLat;
		}	


	//*********************************************************************/

	// Returns the decimal longitude from the degrees, minutes and seconds entered 
	// into the form	

		public static double getLongitude()
		{
			double decLon = -Hunting.longitude;
			return decLon;
		}	


	//***********************************************************************/
	//* Name:    findRecentSunrise							*/
	//* Type:    Function									*/
	//* Purpose: calculate the julian day of the most recent sunrise		*/
	//*		starting from the given day at the given location on earth	*/
	//* Arguments:										*/
	//*   JD  : julian day									*/
	//*   latitude : latitude of observer in degrees				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   julian day of the most recent sunrise					*/
	//***********************************************************************/

		public static double findRecentSunrise(double jd, double latitude, double longitude)
		{
			double julianday = jd;

			double time = calcSunriseUTC(julianday, latitude, longitude);

			return julianday;
		}


	//***********************************************************************/
	//* Name:    findRecentSunset								*/
	//* Type:    Function									*/
	//* Purpose: calculate the julian day of the most recent sunset		*/
	//*		starting from the given day at the given location on earth	*/
	//* Arguments:										*/
	//*   JD  : julian day									*/
	//*   latitude : latitude of observer in degrees				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   julian day of the most recent sunset					*/
	//***********************************************************************/

		public static double findRecentSunset(double jd, double latitude, double longitude)
		{
			double julianday = jd;

			double time = calcSunsetUTC(julianday, latitude, longitude);

			return julianday;
		}


	//***********************************************************************/
	//* Name:    findNextSunrise								*/
	//* Type:    Function									*/
	//* Purpose: calculate the julian day of the next sunrise			*/
	//*		starting from the given day at the given location on earth	*/
	//* Arguments:										*/
	//*   JD  : julian day									*/
	//*   latitude : latitude of observer in degrees				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   julian day of the next sunrise						*/
	//***********************************************************************/

		public static double findNextSunrise(double jd, double latitude, double longitude)
		{
			double julianday = jd;

			double time = calcSunriseUTC(julianday, latitude, longitude);

			return julianday;
		}


	//***********************************************************************/
	//* Name:    findNextSunset								*/
	//* Type:    Function									*/
	//* Purpose: calculate the julian day of the next sunset			*/
	//*		starting from the given day at the given location on earth	*/
	//* Arguments:										*/
	//*   JD  : julian day									*/
	//*   latitude : latitude of observer in degrees				*/
	//*   longitude : longitude of observer in degrees				*/
	//* Return value:										*/
	//*   julian day of the next sunset							*/
	//***********************************************************************/

		public static double findNextSunset(double jd, double latitude, double longitude)
		{
			double julianday = jd;

			double time = calcSunsetUTC(julianday, latitude, longitude);

			return julianday;
		}
//Find Previous and Next Sunrise & Sunset need a check if time returns bad value then go to previous or next day
	//***********************************************************************/
	//* Name:    timeString									*/
	//* Type:    Function									*/
	//* Purpose: convert time of day in minutes to a zero-padded string	*/
	//*		suitable for printing to the form text fields			*/
	//* Arguments:										*/
	//*   minutes : time of day in minutes						*/
	//* Return value:										*/
	//*   string of the format HH:MM:SS, minutes and seconds are zero padded*/
	//***********************************************************************/

		public static String timeString(double minutes)
		// timeString returns a zero-padded string (HH:MM:SS) given time in minutes
		{
			double floatHour = minutes / 60.0;
			double hour = Math.floor(floatHour);
			double floatMinute = 60.0 * (floatHour - Math.floor(floatHour));
			double minute = Math.floor(floatMinute);
			double floatSec = 60.0 * (floatMinute - Math.floor(floatMinute));
			double second = Math.floor(floatSec + 0.5);
			if (second > 59) {
				second = 0;
				minute += 1;
			}
			
			int hourInt = (int)hour;
			int minInt = (int)minute;
			int secInt = (int)second;
			
			String timeStr = hourInt + ":";
			if (minute < 10)	//	i.e. only one digit
				timeStr += "0" + minInt + ":";
			else
				timeStr += minInt + ":";
			if (second < 10)	//	i.e. only one digit
				timeStr += "0" + secInt;
			else
				timeStr += secInt;

			return timeStr;
		}


	//***********************************************************************/
	//* Name:    timeStringShortAMPM							*/
	//* Type:    Function									*/
	//* Purpose: convert time of day in minutes to a zero-padded string	*/
	//*		suitable for printing to the form text fields.  If time	*/
	//*		crosses a day boundary, date is appended.				*/
	//* Arguments:										*/
	//*   minutes : time of day in minutes						*/
	//*   JD  : julian day									*/
	//* Return value:										*/
	//*   string of the format HH:MM[AM/PM] (DDMon)					*/
	//***********************************************************************/

	// timeStringShortAMPM returns a zero-padded string (HH:MM *M) given time in 
	// minutes and appends short date if time is > 24 or < 0, resp.

		public static String timeStringShortAMPM(double minutes, double JD)
		{
			double julianday = JD;
			double floatHour = minutes / 60.0;
			double hour = Math.floor(floatHour);
			double floatMinute = 60.0 * (floatHour - Math.floor(floatHour));
			double minute = Math.floor(floatMinute);
			double floatSec = 60.0 * (floatMinute - Math.floor(floatMinute));
			double second = Math.floor(floatSec + 0.5);

			minute += (second >= 30)? 1 : 0;

			if (minute >= 60) 
			{
				minute -= 60;
				hour ++;
			}

			boolean daychange = false;
			if (hour > 23) 
			{
				hour -= 24;
				daychange = true;
				julianday += 1.0;
			}

			if (hour < 0)
			{
				hour += 24;
				daychange = true;
				julianday -= 1.0;
			}
			
			boolean pm = false;
			
			if (hour > 12)
			{
				hour -= 12;
				pm = true;
			}

	            if (hour == 12)
			{
	              pm = true;
	            }

			if (hour == 0)
			{
				pm = false;
				hour = 12;
			}
			int hourInt = (int)hour;
			int minInt = (int)minute;
			
			String timeStr = hourInt + ":";
			if (minute < 10)	//	i.e. only one digit
				timeStr += "0" + minInt + " " + ((pm)?"PM":"AM");
			else
				timeStr += "" + minInt + " " + ((pm)?"PM":"AM");

			if (daychange) return timeStr + " " + calcDayFromJD(julianday);
			return timeStr;
		}


	//***********************************************************************/
	//* Name:    timeStringAMPMDate							*/
	//* Type:    Function									*/
	//* Purpose: convert time of day in minutes to a zero-padded string	*/
	//*		suitable for printing to the form text fields, and appends	*/
	//*		the date.									*/
	//* Arguments:										*/
	//*   minutes : time of day in minutes						*/
	//*   JD  : julian day									*/
	//* Return value:										*/
	//*   string of the format HH:MM[AM/PM] DDMon					*/
	//***********************************************************************/

	// timeStringAMPMDate returns a zero-padded string (HH:MM[AM/PM]) given time 
	// in minutes and julian day, and appends the short date

		public static String timeStringAMPMDate(double minutes, double JD)
		{
			double julianday = JD;
			double floatHour = minutes / 60.0;
			double hour = Math.floor(floatHour);
			double floatMinute = 60.0 * (floatHour - Math.floor(floatHour));
			double minute = Math.floor(floatMinute);
			double floatSec = 60.0 * (floatMinute - Math.floor(floatMinute));
			double second = Math.floor(floatSec + 0.5);

			minute += (second >= 30)? 1 : 0;

			if (minute >= 60) 
			{
				minute -= 60;
				hour ++;
			}

			if (hour > 23) 
			{
				hour -= 24;
				julianday += 1.0;
			}

			if (hour < 0)
			{
				hour += 24;
				julianday -= 1.0;
			}

			boolean pm = false;
			if (hour > 12)
			{
				hour -= 12;
				pm = true;
			}

	        if (hour == 12)
			{
	        	pm = true;
	        }

			if (hour == 0)
			{
				pm = false;
				hour = 12;
			}
			
			int hourInt = (int)hour;
			int minInt = (int)minute;
			
			String timeStr = hourInt + ":";
			if (minute < 10)	//	i.e. only one digit
				timeStr += "0" + minInt + " " + ((pm)?"PM":"AM");
			else
				timeStr += minInt + " " + ((pm)?"PM":"AM");

			return timeStr + " " + calcDayFromJD(julianday);
		}


	//***********************************************************************/
	//* Name:    timeStringDate								*/
	//* Type:    Function									*/
	//* Purpose: convert time of day in minutes to a zero-padded 24hr time	*/
	//*		suitable for printing to the form text fields.  If time	*/
	//*		crosses a day boundary, date is appended.				*/
	//* Arguments:										*/
	//*   minutes : time of day in minutes						*/
	//*   JD  : julian day									*/
	//* Return value:										*/
	//*   string of the format HH:MM (DDMon)						*/
	//***********************************************************************/

	// timeStringDate returns a zero-padded string (HH:MM) given time in minutes
	// and julian day, and appends the short date if time crosses a day boundary

		public static String timeStringDate(double minutes, double JD)
		{
			double julianday = JD;
			double floatHour = minutes / 60.0;
			double hour = Math.floor(floatHour);
			double floatMinute = 60.0 * (floatHour - Math.floor(floatHour));
			double minute = Math.floor(floatMinute);
			double floatSec = 60.0 * (floatMinute - Math.floor(floatMinute));
			double second = Math.floor(floatSec + 0.5);

			minute += (second >= 30)? 1 : 0;

			if (minute >= 60) 
			{
				minute -= 60;
				hour ++;
			}

			boolean daychange = false;
			if (hour > 23) 
			{
				hour -= 24;
				julianday += 1.0;
				daychange = true;
			}

			if (hour < 0)
			{
				hour += 24;
				julianday -= 1.0;
				daychange = true;
			}

			int hourInt = (int)hour;
			int minInt = (int)minute;
			
			String timeStr = hourInt + ":";
			if (minute < 10)	//	i.e. only one digit
				timeStr += "0" + minInt;
			else
				timeStr += minInt;

			if (daychange) return timeStr + " " + calcDayFromJD(julianday);
			return timeStr;
		}

		
	//***********************************************************************/
	//* Name:    calcSun									*/
	//* Type:    Main Function called by form controls				*/
	//* Purpose: calculate time of sunrise and sunset for the entered date	*/
	//*		and location.  In the special cases near earth's poles, 	*/
	//*		the date of nearest sunrise and set are reported.		*/
	//* Arguments:										*/
	//*   riseSetForm : for displaying results					*/
	//*   latLongForm : for reading latitude and longitude data			*/
	//*   index : daylight saving yes/no select					*/
	//*   index2 : city select index							*/
	//* Return value:										*/
	//*   none											*/
	//*	(fills riseSetForm text fields with results of calculations)	*/
	//***********************************************************************/

		public static void calcSun(Calendar date) 
		{
			double latitude = getLatitude();
			double longitude = getLongitude();
			// System.out.println(latitude);
			// System.out.println(longitude);
			if (true) 
			{
				if((latitude >= -90) && (latitude < -89))
				{
					latitude = -89;
				}
				if ((latitude <= 90) && (latitude > 89))
				{
					latitude = 89;
				}
				
				//*****	Calculate the time of sunrise			

	//*********************************************************************/
	//****************   NEW STUFF   ******   January, 2001   ****************
	//*********************************************************************/

				double JD = calcJD(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DATE));
				String dow = calcDayOfWeek(JD);
				double doy = calcDayOfYear(date);
				double T = calcTimeJulianCent(JD);
				double alpha = calcSunRtAscension(T);
				double theta = calcSunDeclination(T);
				double Etime = calcEquationOfTime(T);

				//riseSetForm["dbug"].value = doy;

	//*********************************************************************/

				double eqTime = Etime;
				double solarDec = theta;
				// Calculate sunrise for this date
				// if no sunrise is found, set flag nosunrise

				boolean nosunrise = false;

				double riseTimeGMT = calcSunriseUTC(JD, latitude, longitude);
				if (false)//Check if riseTimeGMT fails
				{
					nosunrise = true;
				}

				// Calculate sunset for this date
				// if no sunset is found, set flag nosunset

				boolean nosunset = false;
				double setTimeGMT = calcSunsetUTC(JD, latitude, longitude);
				if (false)//Check if setTimeGMT fails
				{
					nosunset = true;
				}

				boolean daySavings = false;  // DaylightSavings Setting
				double zone = 6;//Get GMT Offset

				if (!nosunrise)		// Sunrise was found
				{
					
					double riseTimeLST = riseTimeGMT - (60 * zone);
					if(daySavings){
						riseTimeLST = riseTimeLST + 60;
					}
						//	in minutes
					String riseStr = timeStringShortAMPM(riseTimeLST, JD);
					String utcRiseStr = timeStringDate(riseTimeGMT, JD);
					// System.out.println(riseStr);
					// System.out.println(utcRiseStr);
					Hunting.riseStr = riseStr;
					
				}

				if (!nosunset)		// Sunset was found
				{
					
					double setTimeLST = setTimeGMT - (60 * zone);
					if(daySavings){
						setTimeLST = setTimeLST + 60;
					}
					String setStr = timeStringShortAMPM(setTimeLST, JD);
					String utcSetStr = timeStringDate(setTimeGMT, JD);
					// System.out.println(setStr);
					Hunting.setStr = setStr;
					
				}

				// Calculate solar noon for this date

				double solNoonGMT = calcSolNoonUTC(T, longitude);
				double solNoonLST = solNoonGMT - (60 * zone);
				if(daySavings){
					solNoonLST = solNoonLST + 60;
				}

				String solnStr = timeString(solNoonLST);
				String utcSolnStr = timeString(solNoonGMT);
				// System.out.println(solnStr);
				double tsnoon = calcTimeJulianCent(calcJDFromJulianCent(T) -0.5 + solNoonGMT/1440.0); 

				eqTime = calcEquationOfTime(tsnoon);
				solarDec = calcSunDeclination(tsnoon);
				// System.out.println(Math.floor(100*eqTime)/100);
				// System.out.println(Math.floor(100*solarDec)/100);
				
				//***********Convert lat and long to standard format
				// report special cases of no sunrise

				if(nosunrise)
				{ 
					//riseSetForm["utcsunrise"].value = "";
					// if Northern hemisphere and spring or summer, OR  
					// if Southern hemisphere and fall or winter, use 
					// previous sunrise and next sunset
					/*
					if ( ((latitude > 66.4) && (doy > 79) && (doy < 267)) ||
					   ((latitude < -66.4) && ((doy < 83) || (doy > 263))) )
					{
						newjd = findRecentSunrise(JD, latitude, longitude);
						newtime = calcSunriseUTC(newjd, latitude, longitude)
							 - (60 * zone) + daySavings;
						if (newtime > 1440)
						{
							newtime -= 1440;
							newjd += 1.0;
						}
						if (newtime < 0)
						{
							newtime += 1440;
							newjd -= 1.0;
						}
						riseSetForm["sunrise"].value = 
							timeStringAMPMDate(newtime, newjd);
						riseSetForm["utcsunrise"].value = "prior sunrise";
					}

					// if Northern hemisphere and fall or winter, OR 
					// if Southern hemisphere and spring or summer, use 
					// next sunrise and previous sunset

					else if ( ((latitude > 66.4) && ((doy < 83) || (doy > 263))) ||
						((latitude < -66.4) && (doy > 79) && (doy < 267)) )
					{
						newjd = findNextSunrise(JD, latitude, longitude);
						newtime = calcSunriseUTC(newjd, latitude, longitude)
							 - (60 * zone) + daySavings;
						if (newtime > 1440)
						{
							newtime -= 1440;
							newjd += 1.0;
						}
						if (newtime < 0)
						{
							newtime += 1440;
							newjd -= 1.0;
						}
						riseSetForm["sunrise"].value = 
							timeStringAMPMDate(newtime, newjd);
//						riseSetForm["sunrise"].value = calcDayFromJD(newjd)
//							+ " " + timeStringDate(newtime, newjd);
						riseSetForm["utcsunrise"].value = "next sunrise";
					}
					else 
					{
						alert("Cannot Find Sunrise!");
					}

					// alert("Last Sunrise was on day " + findRecentSunrise(JD, latitude, longitude));
					// alert("Next Sunrise will be on day " + findNextSunrise(JD, latitude, longitude));
	*/
				}

				if(nosunset)
				{ 
/*					riseSetForm["utcsunset"].value = "";
					// if Northern hemisphere and spring or summer, OR
					// if Southern hemisphere and fall or winter, use 
					// previous sunrise and next sunset

					if ( ((latitude > 66.4) && (doy > 79) && (doy < 267)) ||
					   ((latitude < -66.4) && ((doy < 83) || (doy > 263))) )
					{
						newjd = findNextSunset(JD, latitude, longitude);
						newtime = calcSunsetUTC(newjd, latitude, longitude)
							 - (60 * zone) + daySavings;
						if (newtime > 1440)
						{
							newtime -= 1440;
							newjd += 1.0;
						}
						if (newtime < 0)
						{
							newtime += 1440;
							newjd -= 1.0;
						}
						riseSetForm["sunset"].value = 
							timeStringAMPMDate(newtime, newjd);
						riseSetForm["utcsunset"].value = "next sunset";
						riseSetForm["utcsolnoon"].value = "";
					}

					// if Northern hemisphere and fall or winter, OR
					// if Southern hemisphere and spring or summer, use 
					// next sunrise and last sunset

					else if ( ((latitude > 66.4) && ((doy < 83) || (doy > 263))) ||
						((latitude < -66.4) && (doy > 79) && (doy < 267)) )
					{
						newjd = findRecentSunset(JD, latitude, longitude);
						newtime = calcSunsetUTC(newjd, latitude, longitude)
							 - (60 * zone) + daySavings;
						if (newtime > 1440)
						{
							newtime -= 1440;
							newjd += 1.0;
						}
						if (newtime < 0)
						{
							newtime += 1440;
							newjd -= 1.0;
						}
						riseSetForm["sunset"].value = 
							timeStringAMPMDate(newtime, newjd);
						riseSetForm["utcsunset"].value = "prior sunset";
						riseSetForm["solnoon"].value = "N/A";
						riseSetForm["utcsolnoon"].value = "";
					}

					else 
					{
						alert ("Cannot Find Sunset!");
					}
*/				}
			}
		}


}