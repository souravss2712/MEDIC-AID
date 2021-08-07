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

    signinform.signInWithEmailAndPassword(eamil,password).then(cred =>{
        window.location.href = "newappointment.component.html"
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
        uid = uid, 
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

    auth.createUserWithEmailAndPassword(email, password).then(cred =>{
        console.log(cred)
        
                window.location.href = "newappointment.component.html"
                details.ref('users').push({user});
                console.log("Added")
                uid=cred.user.uid();
                signupform.reset()
            
        })
        
    })





//to retrieve data
var dbnameref = details.ref('users');
dbnameref.on('value', function(datasnapshot){
    datasnapshot.forEach(function(childsnap){
        var childData = childsnap.val();
        console.log(childData.user.clinicID)
    })
})

//SET APPOINTMENT 
const appointment = document.querySelector('#appoint')
appointment.addEventListener('sumbit', (e)=> {
    e.preventDefault();
    console.log("Here4")
    
    
    const patientName = appointment['name'].value;
    const age = appointment['age'].value;
    const amountPaid = appointment['amtPaid'].value;
    const date = appointment['date'].value;
    const address = appointment['address'].value;
    const doctorID = appointment['docID'].value;





})



//LOGOUT
// const

// const name = signupform['name'].value;
// const clinicID = signupform['clinicid'].value;

// console.log(email)




// console.log('Here')
