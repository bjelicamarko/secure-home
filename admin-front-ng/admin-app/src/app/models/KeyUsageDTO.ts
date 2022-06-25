export interface KeyUSageDTO {
    certificateSigning: boolean;
    crlSign: boolean;
    dataEncipherment: boolean;
    decipherOnly: boolean;
    digitalSignature: boolean;
    encipherOnly: boolean;
    keyAgreement: boolean;
    keyEncipherment: boolean;
    nonRepudiation: boolean;
}