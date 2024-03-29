export interface AppUserDTO {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    role: string;
    profilePhoto: string;
    verified: boolean;
    locked: boolean;
}