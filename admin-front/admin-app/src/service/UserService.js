/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";
class UserService {

    static login(data) {
        return client({
            url: "api/users/login",
            method: "POST",
            data: data
        })
    }

    static saveUserInLocalStorage(response) {
        var payload = UserService.parseJwt(response.data.accessToken);
        localStorage.clear();
        localStorage.username = payload.sub;
        localStorage.setItem("USER_TOKEN", response.data.accessToken);
        localStorage.setItem("USER_ROLE", payload.role);
        localStorage.setItem("USER_EXPIRES", response.data.expiresIn)
    }

    static parseJwt(tokenContent) {
        var base64Url = tokenContent.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    }

    static isUserLoggedIn() {
        var userData = UserService.getLoggedUserData();
        //If user token present, user is logged.
        if (userData['USER_TOKEN']) return true;
        return false;
    }

    static getLoggedUserData() {
        return {
            userType: UserService._getUserType(),
            userToken: UserService._getUserToken(),
            userExpires: UserService._getUserExpires()
        }
    }

    static _getUserType() {
        return localStorage.getItem('USER_TYPE');;
    }

    static _getUserToken() {
        return localStorage.getItem('USER_TOKEN');
    }

    static _getUserExpires() {
        return localStorage.getItem('USER_EXPIRES');
    };
}
export default UserService;
