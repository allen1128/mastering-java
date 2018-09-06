package org.effective.enumerate;

enum PayDay {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday(PayStrategy.Weekend),
    Sunday(PayStrategy.Weekend);

    PayDay(PayStrategy strategy){
        this.payStrategy = strategy;
    }

    PayDay(){
        this(PayStrategy.Workday);
    }

    float pay(int minutesWorked, float payRate) {
        return payStrategy.pay(minutesWorked, payRate);
    }

    private final PayStrategy payStrategy;

    private final static int payRate = 40;

    private enum PayStrategy{
        Workday{ float overtimePay(int minsWorked, float payRate){
                //double pay on overtime
                return (minsWorked - MINS_PER_SHIFT) > 0 ? (minsWorked - MINS_PER_SHIFT) * payRate * 2 : 0;
            }
        },
        Weekend {float overtimePay(int minsWorked, float payRate) {
            //triple pay on weekend
            return minsWorked * payRate * 3;
        }};

        private static final int MINS_PER_SHIFT = 8 * 40;

        abstract float overtimePay(int minsWorked, float payRate);

        private float pay(int minsWorked, float payRate){
            float basePay = payRate * minsWorked;
            return basePay + overtimePay(minsWorked, payRate);
        }
    }
}
