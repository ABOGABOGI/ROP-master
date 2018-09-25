package rich.on.pay.model;

import java.util.List;

/**
 * Created by Winardi on 9/26/2017.
 */

public class VendorTypeResponse {
    private List<VendorList> vendorLists;

    public List<VendorList> getVendorLists() {
        return vendorLists;
    }

    public void setVendorLists(List<VendorList> vendorLists) {
        this.vendorLists = vendorLists;
    }
}
