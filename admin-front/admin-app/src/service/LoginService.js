/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";

class LoginService {
    static login(data) {
        console.log(data);
        return client({
            url: "api/users/login",
            method: "POST",
            data: data
        })
    }
}
export default LoginService;