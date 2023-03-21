<template>
  <div class="contact-form">
    <h2>Give Feedback</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group" :class="{ 'has-error': v$.name.$error }">
        <label for="name">Name</label>
        <input id="name" type="text" v-model.trim="v$.name.$model">
        <div class="input-errors" v-for="error of v$.name.$errors" :key="error.$uid">
          <div class="error-msg">{{ error.$message }}</div>
        </div>
      </div>
      <div class="form-group" :class="{ 'has-error': v$.email.$error }">
        <label for="email">Email</label>
        <input id="email" type="email" v-model.trim="v$.email.$model">
        <div class="input-errors" v-for="error of v$.email.$errors" :key="error.$uid">
          <div class="error-msg">{{ error.$message }}</div>
        </div>
      </div>
      <div class="form-group" :class="{ 'has-error': v$.feedback.$error }">
        <label for="feedback">Feedback</label>
        <textarea id="feedback" v-model.trim="v$.feedback.$model"></textarea>
        <div class="input-errors" v-for="error of v$.feedback.$errors" :key="error.$uid">
          <div class="error-msg">{{ error.$message }}</div>
        </div>
      </div>
      <button id="submitbutton" type="submit" :disabled="v$.$invalid">Submit</button>
      <div class="success-message" v-if="successMessage">{{ successMessage }}</div>
    </form>
  </div>
</template>

<style scoped>
.contact-form {
  max-width: 400px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}

h2 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

input[type="text"],
input[type="email"],
textarea {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 2px solid #ccc;
  border-radius: 4px;
  transition: border-color 0.2s ease-in-out;
}

input[type="text"]:focus,
input[type="email"]:focus,
textarea:focus {
  outline: none;
  border-color: #2c7be5;
}

.has-error input[type="text"],
.has-error input[type="email"],
.has-error textarea {
  border-color: #e53e3e;
}

.has-error span {
  color: #e53e3e;
  font-size: 0.8rem;
}

button {
  display: block;
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  font-weight: bold;
  color: #fff;
  background-color: #2c7be5;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s ease-in-out;
}

button:disabled {
  background-color: #ccc;
  cursor: default;
}

.success-message {
  margin-top: 1rem;
  text-align: center;
  font-weight: bold;
  color: green;
}
</style>


<script>
import { useContactStore } from '../store/ContactStore'
import useValidate from '@vuelidate/core'
import { required, email, minLength, maxLength } from '@vuelidate/validators'
import { reactive, ref } from 'vue'
import axios from 'axios'

export default {
  setup() {
    const contactStore = useContactStore()

    const state = reactive({
      name: '',
      email: '',
      feedback: ''
    })

    const rules = {
      name: { required },
      email: { required, email },
      feedback: {
        required,
        minLength: minLength(10),
        maxLength: maxLength(200)
      }
    }

    const v$ = useValidate(rules, state)
    const successMessage = ref('')
    
    const submitForm = async () => {
        const isFormValid = await v$.value.$validate()
        if(!isFormValid) return
        axios.post("http://localhost:3000/messages", state)
        .then((response) => {
          contactStore.addMessage(state)
          successMessage.value = "Thank you for your feedback!"
          state.name = ''
          state.email = ''
          state.feedback = ''
          v$.value.$reset()
        })
        .catch((error) => {
          console.log(error)
        })
    }

    return {
      state, v$, contactStore, successMessage, submitForm
    }
  },
}
</script>