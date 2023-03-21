import { defineStore } from 'pinia'

export const useContactStore = defineStore('contactStore', {
    state: () => ({  
        messages: [
            {name: 'asdas', email: "bla", feedback: "great"}
        ] 
    }),
    actions: {
        addMessage(message) {
            this.messages.push(message)
        }
    }
})