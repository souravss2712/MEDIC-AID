
// //to retrieve data
// var dbnameref = details.ref('users');
// dbnameref.on('value', function(datasnapshot){
//     datasnapshot.forEach(function(childsnap){
//         var childData = childsnap.val();
//         console.log(childData.user.clinicID)
//     })
// })




console.log('hereeeeee')

//SET APPOINTMENT 
const appointment = document.querySelector('#appoint')
console.log('hereeeeee1')
appointment.addEventListener('sumbit', (e)=> {
    console.log('hereeeeee3')
    e.preventDefault();
    console.log("Here4")
    
    const patientName = appointment['name'].value
    const age = appointment['age'].value
    const email= appointment['email'].value
    const amountPaid = appointment['amtPaid'].value
    const date = dateObj.toString(appointment['date'].value)
    const time = appointment['time'].value
    const address = appointment['address'].value
    const amtDue = appointment['amtDue'].value;
    const sex = appointment['gender'].value;
    const doctorName = appointment['docName'].value
    console.log('hereeeeee4')
    const appt = {
        patientName:patientName,
        age:age,
        email:email,
        amountPaid:amountPaid,
        date:date,
        address:address,
        sex:sex,
        amtPaid:amtPaid,
        time:time

    
    }

    console.log('hereeeeee5')
    var uid = auth.currentUser.uid; // Gets current user uid as string
    console.log(uid)
    var ref = details.ref('request/' + uid); // Make database ref which points to a child node of request that matches the current user's uid
    ref.push(appt)
    // details.ref(users.uid === uid).push(appt)
    appointment.reset();



})


//LOGOUT
const logout = document.querySelector('#logout')
logout.addEventListener('click', (e) =>
{
    auth.signOut();
    uid = null
    window.location.href = "\auth.component.html"
})




// //SETTINGS
// const setting = document.querySelector('#settings')
// setting.addEventListener('click', (e)=>
// {
    
//     if()
//     details().ref()
// })



// const name = signupform['name'].value;
// const clinicID = signupform['clinicid'].value;

// console.log(email)