package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(Parcel.Serialization.BEAN)
public class User {
    private int id;
    private String username;
    private String login;
    private String email;
    private String fullname;
    private Date dob;
    @SerializedName("m13_question_id")
    private int questionID;
    private String answer;
    @SerializedName("identity_no")
    private String identityNo;
    @SerializedName("profile_picture")
    private String profilePicture;
    @SerializedName("profile_picture_url")
    private String profilePictureUrl;
    @SerializedName("picture_url")
    private String pictureUrl;
    private String addr;
    private String telp;
    @SerializedName("google_auth_code")
    private String googleAuthCode;
    @SerializedName("referral_code")
    private String referralCode;
    private String telpotp;
    private String emailotp;
    private String googleotp;
    private int userstat;
    private String iplog;
    private int is_blacklist;
    private String verihp;
    private int verimail;
    @SerializedName("auth_option")
    private int authOption;
    private double saldo;
    private String password;
    private String dsession;
    @SerializedName("account_no")
    private String account_no;
    private String bank;
    private String account_name;
    @SerializedName("promo_amt")
    private int promoAmt;
    @SerializedName("last_login")
    private Date lastLogin;
    //    private RealmList<RealmProduct> products;
    @SerializedName("completion_stage")
    private Integer completionStage;
    @SerializedName("user_created_at")
    private Date userCreatedAt;
    @SerializedName("prima_status")
    private int primaStatus;
    @SerializedName("is_pin_verified")
    private int isPinVerified;
    @SerializedName("sales_count")
    private int salesCount;
    private boolean isSelected;
    private String userToken;
    @SerializedName("profile_cover")
    private String profileCover;
    private int gender;
    @SerializedName("facebook_url")
    private String facebookUrl;
    @SerializedName("instagram_url")
    private String instagramUrl;
    @SerializedName("usaha_lat")
    private double latitude;
    @SerializedName("usaha_long")
    private double longitude;
    @SerializedName("is_pin_set")
    private boolean isPinSet;
    private UserBalance balances;
    @SerializedName("phone_number")
    private String phoneNumber;
    private IdentityResponse nric;
    private IdentityResponse selfieIC;
    @SerializedName("unread_notification")
    private int unreadNotification;
    private int packages;

    //USAHA
    private String usaha;
    @SerializedName("usaha_kota")
    private String usahaKota;
    @SerializedName("usaha_pic")
    private String usahaPic;
    @SerializedName("usaha_cover")
    private String usahaCover;
    @SerializedName("usaha_desc")
    private String usahaDesc;
    @SerializedName("usaha_telp")
    private String usahaTelp;
    @SerializedName("usaha_addr")
    private String usahaAddr;
    @SerializedName("tipe_usaha")
    private int tipeUsaha;
    @SerializedName("usaha_tagline")
    private String usahaTagline;
    @SerializedName("usaha_operation_open")
    private String openingHour;
    @SerializedName("usaha_operation_close")
    private String closingHour;
    @SerializedName("usaha_created_at")
    private Date usahaCreatedAt;
    @SerializedName("rejected_orders_count")
    private int totalRejectedOrder;
    @SerializedName("products_count")
    private int totalProduct;
    @SerializedName("total_rating")
    private int totalRating;
    @SerializedName("avg_rating")
    private float avgRating;
    @SerializedName("total_order")
    private int totalOrder;
    @SerializedName("other_referral")
    private String otherReferral;
    @SerializedName("address")
    private String address;
    @SerializedName("is_verified")
    private int isVerified;
    @SerializedName("identity_pic")
    private String identityPic;
    @SerializedName("selfie_pic")
    private String selfiePic;
    @SerializedName("is_social_login")
    private int isSocialLogin;
    private String distance;
    //    @SerializedName("vendor_pictures")
//    private RealmList<VendorPicture> vendorPictures;
    @SerializedName("usaha_operation_days")
    private String operatingDays;
    @SerializedName("verifications_status")
    private VerificationStatus verificationStatus;
    @SerializedName("nric_picture_url")
    private String nricPictureUrl;
    @SerializedName("selfie_picture_url")
    private String selfiePictureUrl;
    @SerializedName("accept_policy")
    private boolean acceptPolicy;

    // BANNER
    @SerializedName("pic_url")
    private String picUrl;
    @SerializedName("slug_url")
    private String slugUrl;
    private int type;
    private int userid;

    // LIVE TRANSACTION
    private String name;
    @SerializedName("login_code")
    private String loginCode;
    @SerializedName("business_id")
    private int businessId;
    @SerializedName("session_token_id")
    private int sessionTokenId;
    private OptionModel options;

    public User() {
    }

    public User dummyUser() {
        User dummyUser = new User();
        dummyUser.setId(108);
        dummyUser.setUsaha("Nike Store Mega Mall");
        dummyUser.setOpeningHour("10:00:00");
        dummyUser.setClosingHour("22:00:00");
        dummyUser.setUsahaPic("https://www.pixelstalk.net/wp-content/uploads/2016/06/Nike-Sb-Logo-Wallpaper.jpg");
        dummyUser.setUsahaCover("https://cdn.filepicker.io/api/file/b7KpCA7bSzqq4IhV0CCQ");
        return dummyUser;
    }

    public User(String login, String password, String dsession, String iplog) {
        this.login = login;
        this.password = password;
        this.dsession = dsession;
        this.iplog = iplog;
    }

    public User(String email, String fullname, String username, String telp, String password, int questionID, String answer, String otherReferral, int opsi, String dsession, String iplog) {
        this.email = email;
        this.fullname = fullname;
        this.username = username;
        this.telp = telp;
        this.password = password;
        this.authOption = opsi;
        this.dsession = dsession;
        this.iplog = iplog;
        this.questionID = questionID;
        this.answer = answer;
        this.otherReferral = otherReferral;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getGoogleAuthCode() {
        return googleAuthCode;
    }

    public void setGoogleAuthCode(String googleAuthCode) {
        this.googleAuthCode = googleAuthCode;
    }

    public String getTelpotp() {
        return telpotp;
    }

    public void setTelpotp(String telpotp) {
        this.telpotp = telpotp;
    }

    public String getEmailotp() {
        return emailotp;
    }

    public void setEmailotp(String emailotp) {
        this.emailotp = emailotp;
    }

    public String getGoogleotp() {
        return googleotp;
    }

    public void setGoogleotp(String googleotp) {
        this.googleotp = googleotp;
    }

    public int getUserstat() {
        return userstat;
    }

    public void setUserstat(int userstat) {
        this.userstat = userstat;
    }

    public String getIplog() {
        return iplog;
    }

    public void setIplog(String iplog) {
        this.iplog = iplog;
    }

    public String getVerihp() {
        return verihp;
    }

    public void setVerihp(String verihp) {
        this.verihp = verihp;
    }

    public int getVerimail() {
        return verimail;
    }

    public void setVerimail(int verimail) {
        this.verimail = verimail;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDsession() {
        return dsession;
    }

    public void setDsession(String dsession) {
        this.dsession = dsession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsaha() {
        return usaha;

    }

    public void setUsaha(String usaha) {
        this.usaha = usaha;
    }

    public String getUsahaPic() {
        return usahaPic;
    }

    public void setUsahaPic(String usahaPic) {
        this.usahaPic = usahaPic;
    }

    public String getUsahaDesc() {
        return usahaDesc;
    }

    public void setUsahaDesc(String usahaDesc) {
        this.usahaDesc = usahaDesc;
    }

    public int getPromoAmt() {
        return promoAmt;
    }

    public void setPromoAmt(int promoAmt) {
        this.promoAmt = promoAmt;
    }

//    public RealmList<RealmProduct> getRealmProducts() {
//        return products;
//    }

    public Integer getCompletionStage() {
        return completionStage;
    }

    public void setCompletionStage(Integer completionStage) {
        this.completionStage = completionStage;
    }

    public String getUsahaKota() {
        return usahaKota;
    }

    public void setUsahaKota(String usahaKota) {
        this.usahaKota = usahaKota;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(Date userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public int getAuthOption() {
        return authOption;
    }

    public void setAuthOption(int authOption) {
        this.authOption = authOption;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getUsahaTelp() {
        return usahaTelp;
    }

    public void setUsahaTelp(String usahaTelp) {
        this.usahaTelp = usahaTelp;
    }

    public String getUsahaAddr() {
        return usahaAddr;
    }

    public void setUsahaAddr(String usahaAddr) {
        this.usahaAddr = usahaAddr;
    }

    public int getTipeUsaha() {
        return tipeUsaha;
    }

    public void setTipeUsaha(int tipeUsaha) {
        this.tipeUsaha = tipeUsaha;
    }

    public String getUsahaCover() {
        return usahaCover;
    }

    public void setUsahaCover(String usahaCover) {
        this.usahaCover = usahaCover;
    }

    public int getPrimaStatus() {
        return primaStatus;
    }

    public void setPrimaStatus(int primaStatus) {
        this.primaStatus = primaStatus;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getIsPinVerified() {
        return isPinVerified;
    }

    public void setIsPinVerified(int isPinVerified) {
        this.isPinVerified = isPinVerified;
    }

    public int getIs_blacklist() {
        return is_blacklist;
    }

    public void setIs_blacklist(int is_blacklist) {
        this.is_blacklist = is_blacklist;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getProfileCover() {
        return profileCover;
    }

    public void setProfileCover(String profileCover) {
        this.profileCover = profileCover;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(String closingHour) {
        this.closingHour = closingHour;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public Date getUsahaCreatedAt() {
        return usahaCreatedAt;
    }

    public void setUsahaCreatedAt(Date usahaCreatedAt) {
        this.usahaCreatedAt = usahaCreatedAt;
    }

    public int getTotalRejectedOrder() {
        return totalRejectedOrder;
    }

    public void setTotalRejectedOrder(int totalRejectedOrder) {
        this.totalRejectedOrder = totalRejectedOrder;
    }

    public String getOtherReferral() {
        return otherReferral;
    }

    public void setOtherReferral(String otherReferral) {
        this.otherReferral = otherReferral;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getIdentityPic() {
        return identityPic;
    }

    public void setIdentityPic(String identityPic) {
        this.identityPic = identityPic;
    }

    public String getSelfiePic() {
        return selfiePic;
    }

    public void setSelfiePic(String selfiePic) {
        this.selfiePic = selfiePic;
    }

    public int getIsSocialLogin() {
        return isSocialLogin;
    }

    public void setIsSocialLogin(int isSocialLogin) {
        this.isSocialLogin = isSocialLogin;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

//    public List<VendorPicture> getVendorPictures() {
//        return vendorPictures;
//    }

    public String getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(String operatingDays) {
        this.operatingDays = operatingDays;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSlugUrl() {
        return slugUrl;
    }

    public void setSlugUrl(String slugUrl) {
        this.slugUrl = slugUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsahaTagline() {
        return usahaTagline;
    }

    public void setUsahaTagline(String usahaTagline) {
        this.usahaTagline = usahaTagline;
    }

    public boolean isPinSet() {
        return isPinSet;
    }

    public void setPinSet(boolean pinSet) {
        isPinSet = pinSet;
    }


    public UserBalance getBalances() {
        return balances;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getSessionTokenId() {
        return sessionTokenId;
    }

    public void setSessionTokenId(int sessionTokenId) {
        this.sessionTokenId = sessionTokenId;
    }

    public OptionModel getOptions() {
        return options;
    }

    public IdentityResponse getNric() {
        return nric;
    }

    public void setNric(IdentityResponse nric) {
        this.nric = nric;
    }

    public IdentityResponse getSelfieIC() {
        return selfieIC;
    }

    public void setSelfieIC(IdentityResponse selfieIC) {
        this.selfieIC = selfieIC;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public void setBalances(UserBalance balances) {
        this.balances = balances;
    }

    public String getNricPictureUrl() {
        return nricPictureUrl;
    }

    public void setNricPictureUrl(String nricPictureUrl) {
        this.nricPictureUrl = nricPictureUrl;
    }

    public String getSelfiePictureUrl() {
        return selfiePictureUrl;
    }

    public void setSelfiePictureUrl(String selfiePictureUrl) {
        this.selfiePictureUrl = selfiePictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getUnreadNotification() {
        return unreadNotification;
    }

    public void setUnreadNotification(int unreadNotification) {
        this.unreadNotification = unreadNotification;
    }

    public boolean isAcceptPolicy() {
        return acceptPolicy;
    }

    public void setAcceptPolicy(boolean acceptPolicy) {
        this.acceptPolicy = acceptPolicy;
    }

    public int getPackages() {
        return packages;
    }

    public void setPackages(int packages) {
        this.packages = packages;
    }
}

