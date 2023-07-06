<template>
  <div style="margin: 0 auto; width: 80%">
    <Toast position="top-right" />

   
    <h1>Sistema de Gestion de Tareas</h1>
    <Menubar :model="items" />
    <br />
    <DataTable id="tabla" :value="tareas"  :selection.sync="selectedTarea" selectionMode="single" dataKey="id" :paginator="true" :rows="5">
      
      
      <Column hidden field="id" header="ID"></Column>
      <Column field="titulo"  :sortable="true" header="titulo"></Column>
      <Column field="descripcion"  :sortable="true" header="descripcion"></Column>
      <Column field="fechaVencimiento"  :sortable="true" header="fechaVencimiento"></Column>
      <Column field="completada"  :sortable="true" header="completada">
        <template #body="tareas">
          <Badge v-if="tareas.data.completada" value="Completada" severity="success"></Badge>
          <Badge v-else value="No Completada" severity="danger"></Badge>
        </template>

      </Column>
     
    </DataTable>



    <Dialog header="Crear Tarea" :visible.sync="displayModalSave" :modal="true">
      <div class="card" style="margin-top: 20px">

        <span class="p-float-label">
          <InputText style="width: 260px;" id="titulo" type="text" v-model="tarea.titulo"  />
          <label for="titulo">Titulo</label>
        </span>
        <br>
        <br>
        <span class="p-float-label">
          <Textarea id="descripcion" v-model="tarea.descripcion" rows="5" cols="30" />
          <label for="descripcion">Descripcion</label>

        </span>
        <br>
        <br>
        <InputText style="width: 260px;" id="titulo" type="date" v-model="tarea.fechaVencimiento" />

      </div>

      <template #footer>
        <Button label="Guardar" icon="pi pi-check" @click="save"  />
        <Button label="Cancelar" icon="pi pi-times" @click="closeModalSave" class="p-button-secondary"/>
      </template>
    </Dialog>


    <Dialog header="Editar Tarea" :visible.sync="displayModalEdit" :modal="true">
      <div class="card" style="margin-top: 20px">

        <span class="p-float-label">
          <InputText style="width: 260px;" id="titulo" type="text" v-model="tarea.titulo"  />
          <label for="titulo">Titulo</label>
        </span>
        <br>
        <br>
        <span class="p-float-label">
          <Textarea id="descripcion" v-model="tarea.descripcion" rows="5" cols="30" />
          <label for="descripcion">Descripcion</label>

        </span>
        <br>
        <br>
        <InputText style="width: 260px;" id="titulo" type="date" v-model="tarea.fechaVencimiento" />

      </div>

      <template #footer>
        <Button label="Editar" icon="pi pi-check" @click="edit"  />
        <Button label="Cancelar" icon="pi pi-times" @click="closeModalEdit" class="p-button-secondary"/>
      </template>
    </Dialog>

    <Dialog header="Atención!" :visible.sync="displayModalDelete" :modal="true">
      <div class="card" style="margin-top: 20px">

        <div class="confirmation-content">
          <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem; margin-right: 20px" />
          <span>¿Estas seguro de eliminar esta tarea?</span>
        </div>

      </div>
      <template #footer>
        <Button label="Eliminar" icon="pi pi-check" @click="deleted"  />
        <Button label="Cancelar" icon="pi pi-times" @click="closeModalDelete" class="p-button-secondary"/>
      </template>
    </Dialog>

    <Dialog header="Atención!" :visible.sync="displayModalCompleted" :modal="true">
      <div class="card" style="margin-top: 20px">

        <div class="confirmation-content">
          <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem; margin-right: 20px" />
          <span>¿Estas seguro de completar esta tarea?</span>
        </div>

      </div>
      <template #footer>
        <Button label="Completar" icon="pi pi-check" @click="completed" class="p-button-success" />
        <Button label="Cancelar" icon="pi pi-times" @click="closeModalCompleted" class="p-button-secondary"/>
      </template>
    </Dialog>

    <Dialog header="Detalle de la Tarea" :visible.sync="displayBasic" :containerStyle="{width: '50vw'}">
      <h3>Titulo de la tarea</h3>
      <p>{{tarea.titulo}}</p>

     
      <h3>Descripción de la tarea</h3>
      <p>{{tarea.descripcion}}</p>

    
      <h3>Fecha de vencimiento</h3>
      <p>{{tarea.fechaVencimiento}}</p>

     
      <h3>Estado de la tarea</h3>
      <Badge v-if="tarea.completada" value="Completada" severity="success"></Badge>
      <Badge v-else value="No Completada" severity="danger"></Badge> 
       
      <template #footer>
        <Button label="Cerrar" icon="pi pi-times" @click="closeBasic" class="p-button-text"/>
      </template>
    </Dialog>

  </div>
</template>


<script>
import TareaService from "../service/TareaService";
export default {
  name: "Sistema",
  data() {
    return {
      tareas: null,
      tamañoLista : null,
      tarea: {
        id: null,
        titulo: null,
        descripcion: null,
        fechaVencimiento: null,
        completada: null,
      },

      selectedTarea : {
        id: null,
        titulo: null,
        descripcion: null,
        fechaVencimiento: null,
        completada: null,
      },

      items: [
        {
          label: "Nuevo",
          icon: "pi pi-fw pi-plus",
          command: () => {
            this.showSaveModal();
          },
        },
        {
          label: "Editar",
          icon: "pi pi-fw pi-pencil",
          command: () => {
            this.showEditModal();
          },
        },
        {
          label: "Eliminar",
          icon: "pi pi-fw pi-trash",
          command: () => {
            this.showDeleteModal();
          },
        },
        {
          label: "Completar",
          icon: "pi pi-fw pi-check",
          command: () => {
            this.showCompleteModal();
          },
        },
        {
          label: "Detalle",
          icon: "pi pi-fw pi-ellipsis-v",
          command: () => {
            this.showDetailModal();
          },
        },
      ],


      displayModalSave: false,
      displayModalEdit: false,
      displayModalDelete: false,
      displayModalCompleted: false,
      displayBasic: false,
    };
  },

  tareaService: null,

  created() {
    this.tareaService = new TareaService();
    
  },

  mounted() {
    this.getAll();
    
  },

  methods: {

    showSaveModal() {
      
      this.displayModalSave = true;
    },

    showEditModal(){
      this.tarea = this.selectedTarea;
   
      if (this.tamañoLista <= 0) {
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'No hay tareas para editar', life: 1000});
        this.displayModalEdit = false;
      }else if(this.selectedTarea.id == null){
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'Seleccione una tarea', life: 1000});
        this.displayModalEdit = false;
      }else{
        this.displayModalEdit = true;
      }
    },

    showDeleteModal(){
      this.tarea = this.selectedTarea;
      if (this.tamañoLista <= 0) {
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'No hay tareas para eliminar', life: 1000});
        this.displayModalDelete = false;
      }else if(this.selectedTarea.id == null){
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'Seleccione una tarea', life: 1000});
        this.displayModalDelete = false;
      }else{
        this.displayModalDelete = true;
      }
      
    },

    showCompleteModal(){
      this.tarea = this.selectedTarea;
      if (this.tamañoLista <= 0) {
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'No hay tareas para completar', life: 1000});
        this.displayModalCompleted = false;
      }else if(this.selectedTarea.id == null){
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'Seleccione una tarea', life: 1000});
        this.displayModalCompleted = false;
      }else{
        this.displayModalCompleted = true;
      }
      
      
    },

    showDetailModal(){
      this.tarea = this.selectedTarea;
      
      if (this.tamañoLista <= 0) {
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'No hay tarea para detallar', life: 1000});
        this.displayBasic = false;
      }else if(this.selectedTarea.id == null){
        this.$toast.add({severity:'info', summary: 'Atencion!', detail:'Seleccione una tarea', life: 1000});
        this.displayBasic = false;
      }else{
        this.displayBasic = true;
        this.getOne();
      }
      
    },

    edit() {
      if(this.tarea.titulo == "" && this.tarea.descripcion == "" && this.tarea.fechaVencimiento == "" ){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Llene el formulario', life: 1000});
        
      }else if(this.tarea.titulo == ""){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Ingrese un titulo', life: 1000});
        
      }else if(this.tarea.descripcion == ""){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Ingrese una descrición', life: 1000});
        
      }else if(this.tarea.fechaVencimiento == ""){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Ingrese una fecha de vencimiento', life: 1000});
        
      }else{
        this.tareaService.edit(this.selectedTarea).then(data=>{
          if (data.status == 200) {
            this.displayModalEdit = false;
            this.tarea = {
              id: null,
              titulo: null,
              descripcion: null,
              fechaVencimiento: null,
              completada: null,
            }
            this.selectedTarea = {
              id: null,
              titulo: null,
              descripcion: null,
              fechaVencimiento: null,
              completada: null,
            }
            this.$toast.add({severity:'success', summary: 'Tarea Modificada!', detail:'Se modifico exitosamente', life: 3000});
            this.getAll();
          }else{
            this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Algo salio mal :(', life: 3000});
          }
        })
      }
    },

    deleted() {
      this.tareaService.delete(this.selectedTarea.id).then(data=>{
        if (data.status == 200) {
          this.displayModalDelete = false;
          this.tarea = {
            id: null,
            titulo: null,
            descripcion: null,
            fechaVencimiento: null,
            completada: null,
          }
          this.selectedTarea = {
            id: null,
            titulo: null,
            descripcion: null,
            fechaVencimiento: null,
            completada: null,
          }
          this.$toast.add({severity:'success', summary: 'Exito!', detail:'La tarea se elimino exitosamente', life: 1000});
          this.getAll();
        }else{
          this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Algo salio mal :(', life: 3000});
        }
      })
    },

    completed() {
      this.tareaService.completed(this.selectedTarea.id).then(data=>{
        if(this.selectedTarea.completada == true){
          this.displayModalCompleted = false;
          this.tarea = {
            id: null,
            titulo: null,
            descripcion: null,
            fechaVencimiento: null,
            completada: null,
          }

          this.selectedTarea = {
            id: null,
            titulo: null,
            descripcion: null,
            fechaVencimiento: null,
            completada: null,
          }
          this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'La tarea ya ha sido completada', life: 1000});
          
        }else if (data.status == 200) {
          this.displayModalCompleted = false;
          this.tarea = {
            id: null,
            titulo: null,
            descripcion: null,
            fechaVencimiento: null,
            completada: null,
          }
          this.selectedTarea = {
            id: null,
            titulo: null,
            descripcion: null,
            fechaVencimiento: null,
            completada: null,
          }
          this.$toast.add({severity:'success', summary: 'Exito!', detail:'La tarea se completo exitosamente', life: 1000});
          this.getAll();
        }else{
          this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Algo salio mal :(', life: 3000});
        }
      })
    },

    save(){
      if(this.tarea.titulo == null && this.tarea.descripcion == null && this.tarea.fechaVencimiento == null ){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Llene el formulario', life: 1000});
        
      }else if(this.tarea.titulo == null){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Ingrese un titulo', life: 1000});
        
      }else if(this.tarea.descripcion == null){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Ingrese una descrición', life: 1000});
        
      }else if(this.tarea.fechaVencimiento == null){
        this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Ingrese una fecha de vencimiento', life: 1000});
        
      }else{
        this.tareaService.save(this.tarea).then(data=>{
          if (data.status == 200) {
            this.displayModalSave = false;
            this.tarea = {
              id: null,
              titulo: null,
              descripcion: null,
              fechaVencimiento: null,
              completada: null,
            }
            this.$toast.add({severity:'success', summary: 'Tarea Registrada!', detail:'Se registro exitosamente', life: 3000});
            this.getAll();
          }else{
            this.$toast.add({severity:'error', summary: 'Lo siento!', detail:'Algo salio mal :(', life: 3000});
          }
        })
      }
      
    },

    closeModalSave(){
      this.displayModalSave = false;
    },

    closeModalEdit(){
      this.displayModalEdit = false;
    },

    closeModalDelete(){
      this.displayModalDelete = false;
    },

    closeModalCompleted(){
      this.displayModalCompleted = false;
    },

    closeBasic(){
      this.displayBasic = false;
    },

    getAll() {
      this.tareaService.getAllTareas().then((data) => {
        this.tareas = data.data;
        this.tamañoLista = this.tareas.length;
      });
    },

    getOne() {
      this.tareaService.getTarea(this.selectedTarea.id).then((data) => {
        this.tarea = data.data;
        
      });
    },
  },
};
</script>

<style>
</style>