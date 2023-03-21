<template>
    <html>

    <head>

    </head>

    <body>
        <div class="container">
            <div class="title">Login Page</div>

            <form @submit.prevent="handleSubmit">
                <div class="input-group">
                    <label for="username">Username</label>
                    <input type="text" v-model="username" placeholder="Username" />
                </div>

                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" v-model="password" placeholder="Password" />
                </div>

                <button type="submit" class="button">Login</button>
            </form>
        </div>
    </body>

    </html>
</template>

<script setup>
    import { ref } from 'vue'
    import { useLoginStore } from '../store/LoginStore'
    import { useRoute, useRouter } from 'vue-router';

    const loginStore = useLoginStore();
    const username = ref('')
    const password = ref('')

    const route = useRoute();
    const router = useRouter();

    const handleSubmit = async () => {
        const credentials = {
            username: username.value,
            password: password.value
        };
        try {
            const response = await loginStore.login(credentials);
            console.log(response.status + ' authentication successful.');

            const redirectFrom = route.query.redirectFrom || { name: 'Home' };
            router.push(redirectFrom);
        } catch (error) {
            if (error.response) {
                console.log(error.response.status + ' authentication failed, bad credentials.');
            } else {
                console.error(error);
            }
        }
    }
</script>

<style scoped>
body {
    font-family: Arial, sans-serif;
    /* background-color: #f0f2f5; */
}

.container {
    width: 100%;
    max-width: 400px;
    margin: 50px auto;
    padding: 30px;
    background-color: white;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.title {
    font-size: 24px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}

.input-group {
    margin-bottom: 20px;
}

label {
    display: block;
    font-size: 14px;
    margin-bottom: 5px;
}

input {
    display: block;
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 5px;
    outline: none;
    transition: border-color 0.3s;
}

input:focus {
    border-color: #009688;
}

.button {
    display: block;
    width: 100%;
    padding: 10px;
    font-size: 16px;
    background-color: #009688;
    border: none;
    border-radius: 5px;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s;
}

.button:hover {
    background-color: #00796b;
}
</style>
