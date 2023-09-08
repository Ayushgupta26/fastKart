export class UserInfo{
    "name": String;
    "userName":String;
    "password":String;
    "role":String;

    public UserInfo(name:String,userName:String,password:String){
        this.name=name;
        this.userName=userName;
        this.password=password;
    }

    public setRole(role:String){
        this.role=role;
    }
}