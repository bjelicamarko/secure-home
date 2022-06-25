import { CertificateDataDTO } from "./CertificateDataDTO";
import { ExtendedKeyUsageDTO } from "./ExtendedKeyUsageDTO";
import { KeyUSageDTO } from "./KeyUsageDTO";

export interface CertificateSigningDTO {
    certificateDataDTO: CertificateDataDTO;
    keyUsageDTO: KeyUSageDTO
    extendedKeyUsageDTO: ExtendedKeyUsageDTO;
    ca: boolean;
}