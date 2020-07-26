<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <h1>{{ hello }}</h1>
        <span v-html="rawHtml"></span>
        <div v-bind:id="dynamicId">
            div id attribute = dynamicId111
        </div>

        <button v-bind:disabled="buttonStatus.btn1">disabled=null</button>
        <button v-bind:disabled="buttonStatus.btn2">disabled=undefined</button>
        <button v-bind:disabled="buttonStatus.btn3">disabled=false</button>
        <button v-bind:disabled="buttonStatus.btn4">disabled=1</button>
        <button v-bind:disabled="buttonStatus.btn5">disabled=true</button>

        <p v-if="pSeen">现在你看到我了</p>
        <p v-if="pSeen2 == 6">现在你看到我了（pSeen2 == 6）</p>

        <div class="testA">
            <a v-bind:href="fenxiangz" target="_blank">fenxiangz.com</a>
            <a :href="fenxiangz" target="_blank">fenxiangz.com</a>

            <a v-on:click="showAlert('HHHH')">alert('HHHH')</a>
            <a @click="showAlert('HHHH')">alert('HHHH')</a>
        </div>


        <h1>翻转消息： {{ reverseMsg }}</h1>
    </div>
</template>

<script>
    export default {
        name: 'HelloWorld',
        props: {
            msg: String
        },
        data() {
            return {
                hello: "000",
                rawHtml: '<balel style="color: red">AAAAA</balel>',
                dynamicId: 'dynamicId111',
                buttonStatus: {
                    btn1: null,
                    btn3: false,
                    btn4: 1,
                    btn5: true,
                },
                pSeen: true,
                pSeen2: 6,
                fenxiangz: 'https://www.fenxiangz.com',

            }
        },
        created() {
            this.hello = "Hello, Hassan"
        },
        methods: {
            showAlert: function (msg) {
                window.alert(msg);
            }

        },
        computed:{
            reverseMsg: function () {
                return this.hello.split("").reverse().join("");
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
        display: block;
        margin: 5px 5px;
    }

    .testA > a {
        display: inline-block;
    }

    button {
        margin: 5px 5px;
    }

    div {
        margin: 5px 5px;
    }
</style>
