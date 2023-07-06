import axios from "axios";

export default class TareaService{

    url = "http://localhost:8080/"

    getAllTareas(){
        return axios.get(this.url + "tareas"); 
    }

    getTarea(id){
        return axios.get(this.url + "tareas/"+id); 
    }

    save(tarea){
        return axios.post(this.url + "tareas", tarea); 
    }

    edit(tarea){
        return axios.put(this.url + "tareas/"+tarea.id, tarea); 
    }

    delete(id){
        return axios.delete(this.url + "tareas/"+id); 
    }

    completed(id){
        return axios.put(this.url + "completada/"+id); 
    }
}