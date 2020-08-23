<template>
    <div id="app">
        <img alt="Vue logo" src="./assets/logo.png">
        <HelloWorld msg="Welcome to Your Vue.js App"/>

        <TodoList/>
    </div>
</template>

<script>
    import HelloWorld from './components/HelloWorld.vue'
    import TodoList from './components/todo/TodoList.vue'

    let nextTodoId = 1;

    export default {
        name: 'App',
        components: {
            HelloWorld,
            TodoList
        },
        data() {
            return {
                todos: [
                    {
                        id: nextTodoId++,
                        text: 'Learn Vue'
                    },
                    {
                        id: nextTodoId++,
                        text: 'Learn about single-file components'
                    },
                    {
                        id: nextTodoId++,
                        text: 'Fall in love'
                    }
                ],
                nextTodoId: nextTodoId
            }
        }
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
