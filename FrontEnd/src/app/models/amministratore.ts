export class Amministratore {


    id: Number;
    nome: String;
    cognome: String;
    email: String;
    role: String;
    
    

    constructor(id: Number, nome: String, cognome: String, email: String, role: String ) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.role = role;
        
    }

    getId() {
        return this.id;
    }

    getNome() {
        return this.nome;
    }


    getRole() {
        return this.role;
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