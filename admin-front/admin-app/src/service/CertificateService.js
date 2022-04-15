/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";

class CertificateService {
    
    static createCertificate(certDTO) {
        return client({
            url: "api/certificates",
            method: "POST",
            data: certDTO
        })
    }
    
}
export default CertificateService;
