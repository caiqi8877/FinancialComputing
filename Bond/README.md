In this task I will program a yield curve object and a bond object. Both object are going to interact with each other, and therefore we will describe them together. In addition, for simplicity, we are going to assume continuous compounding. For the question we will use the following yield curve.
Year r
1 2% 2 2.3% 3 3%

I will design a yield curve object that has the following methods:
1. public double getInterestRate(double time); returns the per-year interest rate for a given duration in continues compounding.
2. public double getForwardRate(double t0, double t1); return the forward rate between two dates. In continues compounding this translates to t 0
3. public double getDiscountFactor(double t); Should return the discount factor for a given duration.

My object should be able to create an object from a list of bonds (some Zero coupon some not). That is I should have at least a constructor that takes ”List¡Bond bonds” as one of its arguments.

I will be able to represents at least a coupon bearing and non-coupon bearing bonds. A bond should be able to provide at least the following methods.

1. public double getPrice(); The price of the bound.
2. public double getCopon(); The bond’s coupon if any.
3. public double getMaturity(); The bond maturity in years.
4. public double getFaceValue(); The bond’s face value.
5. public Map<Double,Double> getCashFlow(); This method can be used to get a map with the key being time and the value being the cash flow distributed at that period.

I will implement the following methods too:
1. public double getPrice(YieldCurve ycm Bond bond); Calculate the bond’s fair price given a yield curve object.
2. public double getYTM(Bond bond, double price); returns the yield-to-maturity of the bond for a particular price.
3. public double getPrice(Bond bond, double ytm); returns the bond’s fair price given the yield to maturity.
