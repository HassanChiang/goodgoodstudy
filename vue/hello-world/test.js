function* gen() {
    console.log('Hello' + (yield)); // OK
    console.log('Hello' + (yield 123)); // OK
    return 666;
}

let a = gen();
console.log(a.next());
console.log(a.next());
console.log(a.next());
console.log(a.next());