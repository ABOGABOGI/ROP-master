package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by teddysantya on 22/03/18.
 */

public class APIModels {
    private String token;
    private User user;
    private String note;
    private List<BankInfo> banks;
    @SerializedName("bank_accounts")
    private List<BankAccount> bankAccounts;
    private List<TopUpRequest> topup;
    private List<Banner> banners;
    @SerializedName("upgrade_request")
    private UpgradeRequest upgradeRequest;
    @SerializedName("upgrade_requests")
    private List<UpgradeRequest> upgradeRequests;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<BankInfo> getBanks() {
        return banks;
    }

    public void setBanks(List<BankInfo> banks) {
        this.banks = banks;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public List<TopUpRequest> getTopup() {
        return topup;
    }

    public void setTopup(List<TopUpRequest> topup) {
        this.topup = topup;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public UpgradeRequest getUpgradeRequest() {
        return upgradeRequest;
    }

    public void setUpgradeRequest(UpgradeRequest upgradeRequest) {
        this.upgradeRequest = upgradeRequest;
    }

    public List<UpgradeRequest> getUpgradeRequests() {
        return upgradeRequests;
    }

    public void setUpgradeRequests(List<UpgradeRequest> upgradeRequests) {
        this.upgradeRequests = upgradeRequests;
    }
}
