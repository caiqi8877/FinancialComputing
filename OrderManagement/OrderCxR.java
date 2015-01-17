package edu.nyu.compfin14;

import edu.nyu.compfin14.Message;

public interface OrderCxR extends Message{

       /**
        * The size for this order. Note, CxR will not cause an order to change sides!
        * zero indicate cancel.
        * @return The size of the order. Negative for sell.
        */
       public int getSize();

       /**
        * @return The orderId for this CxR
        */
       public String getOrderId();

       /**
        * @return The limit price for the order. This will be populated.
        */
       public double getLimitPrice();
}
