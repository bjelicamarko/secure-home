/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";

class CSRService {
    
    static saveCSR(CSRequest) {
        return client({
            url: "api/csrs",
            method: "POST",
            data: CSRequest
        })
    }

    static getAllVerifiedCSRs() {
        return client({
            url: "api/csrs/verified",
            method: "GET"
        })
    }

    static getCSR(id) {
        return client({
            url: "api/csrs/" + id,
            method: "GET"
        })
    }
}
export default CSRService;
