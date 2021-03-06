package muses.art.entity.user;

import muses.art.entity.commodity.Commodity;
import muses.art.entity.filter.Filter;
import muses.art.entity.operation.Comment;
import muses.art.entity.operation.UserFavCommodity;
import muses.art.entity.trade.Address;
import muses.art.entity.trade.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames={"nickname"})})
public class User { // 用户个人信息
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username; // 用户名

    @Column(name = "password", nullable = false)
    private String password; // 密码

    @Column(name = "avatar")
    private String avatar; // 头像的地址

    @Column(name = "nickname")
    private String nickname; // 昵称

    @Column(name = "birthday")
    private Date birthday; // 生日

    @Column(name = "gender")
    private Integer gender;  // 性别 0-男 1-女

    @Column(name = "mobile")
    private String mobile; // 手机号

    @Column(name = "email")
    private String email; // 电子邮箱

    @Column(name = "level")
    private Integer level; // 用户等级

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.DETACH)
    private List<Filter> filters; // 用户制作的所有滤镜 一对多

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Filter> favFilters; // 用户喜欢的所有滤镜 一对多

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserFavCommodity> favCommodities; // 用户喜欢的所有商品 一对多

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Address> addresses;

    @Column(name = "token")
    private String token;

    public List<Comment> getComments() {
        return comments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Filter> getFavFilters() {
        return favFilters;
    }

    public void setFavFilters(List<Filter> favFilters) {
        this.favFilters = favFilters;
    }

    public List<UserFavCommodity> getFavCommodities() {
        return favCommodities;
    }

    public void setFavCommodities(List<UserFavCommodity> favCommodities) {
        this.favCommodities = favCommodities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", level='" + level + '\'' +
                ", filters=" + filters +
                '}';
    }
}
