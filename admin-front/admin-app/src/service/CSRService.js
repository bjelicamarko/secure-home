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
}
export default CSRService;
