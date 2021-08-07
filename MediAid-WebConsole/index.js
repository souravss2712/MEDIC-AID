//SIGN IN
var uid=null
const signinform = document.querySelector('#signinform')
signinform.addEventListener('submit', (e) =>
{
    e.preventDefault();
    console.log('Here2')
    console.log(signinform['sign-in-email'].value)
    const email = signinform['sign-in-email'].value;
    const password = signinform['sign-in-password'].value;


    auth.signInWithEmailAndPassword(email,password).then(cred =>{
        console.log("Logged in")
        window.location.href = "\appointment.html"
    })
     
})




//SIGN UP
const signupform = document.querySelector('#signupform')
signupform.addEventListener('submit', (e)=>{
    e.preventDefault();
    console.log("Here3")
    const email = signupform['email'].value;    
    const password = signupform['password'].value;
    const displayName = signupform['name'].value;
    const clinicid = signupform['clinicid'].value;
    const phoneNumber = signupform['number'].value;

    const user = {
        userName:displayName,
        clinicID:clinicid,
        phoneNumber:phoneNumber,
        uniqueID : uid, 
        doctorList:{
            doctorID:null,
            appointments:{
                patientName:null,
                patientAge:null,
                amountPaid: null,
                amountDue:null,
                date:null,
                address:null
        
            }
        }

    }
console.log("Creatinggg.....")
    auth.createUserWithEmailAndPassword(email, password).then(cred =>{
        console.log(cred)
                details.ref('users').push({user});
                console.log("Added")
                // uid=cred.user.uid();
                signupform.reset()
                window.location.href = "\appointment.html"
               
            
        })
        
    })







// console.log('Here')
