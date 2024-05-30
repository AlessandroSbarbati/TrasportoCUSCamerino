export class Utente {

    id: Number;
    nome: String;
    username:String;
    cognome: String;
    email: String;
    role: String;
    telefono : String;
    
    

    constructor(id: Number, nome: String, cognome: String, email: String, username: String, role: String, telefono: String ) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.role = role;
        this.telefono = telefono;
        
    }

    getId() {
        return this.id;
    }

    getNome() {
        return this.nome;
    }

    getUsername() {
        return this.username;
    }

    getRole() {
        return this.role;
    }

    gettelefono() {
        return this.telefono;
    }

    settelefono(telefono : string) {
        if(telefono == ""){
            throw console.error("Nome can't be null.");
        }
        this.telefono = telefono; 
    }




    getCognome() {
        return this.cognome;
    }

    getEmail() {
        return this.email;
    }

    
    
    
    setNome(nome: String){
        if(nome == ""){
            throw console.error("Nome can't be null.");
        }
        this.nome = nome; 
    }

    setUsername(username: String){
        if(username == ""){
            throw console.error("Nome can't be null.");
        }
        this.username = username; 
    }

    setRole(role: String){
        if(role == ""){
            throw console.error("Nome can't be null.");
        }
        this.role = role; 
    }

   
   

    setEmail(email : String){
        if(email == null){
            throw console.error("Id can't be null.");
        }
        this.email = email; 
    }
}