package com.example.agriguard.modules.main.user.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.agriguard.R
import com.example.agriguard.modules.main.MainNav
import com.example.agriguard.modules.main.farmer.viewmodel.FarmersViewModel
import com.example.agriguard.modules.main.setting.saveBitmapToUri
import com.example.agriguard.modules.main.user.model.dto.AddressDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.example.agriguard.modules.main.user.service.UserService
import com.example.agriguard.modules.main.user.viewmodel.UserViewModel
import com.example.agriguard.modules.shared.ext.hashPassword
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun UserFormUI(
    title: String = "Create Account",
    userDto: UserDto? = null,
    currentUser: UserDto,
    onSubmit: (UserDto) -> Unit,
    navController: NavController,
    includePassword: Boolean = true,
    addressDto: AddressDto?,
    userService: UserService = hiltViewModel<UserViewModel>().userService
) {
    val listOfLabel = mutableListOf(
        "First Name", "Middle Name", "Last Name", "Date Of Birth", "Address",
        "Mobile Number", "Email"
    )
    if (includePassword) listOfLabel.add("Password")

    val statesValue = remember {
        listOfLabel.associateWith {
            mutableStateOf(
                when (it) {
                    "First Name" -> userDto?.firstName?.trim() ?: ""
                    "Middle Name" -> userDto?.middleName?.trim() ?: ""
                    "Last Name" -> userDto?.lastName?.trim() ?: ""
                    "Date Of Birth" -> userDto?.dateOfBirth ?: ""
                    "Address" -> {
                        if (currentUser.isTechnician) currentUser.address ?: ""
                        else userDto?.address?.trim() ?: addressDto?.name ?: ""
                    }                    "Mobile Number" -> userDto?.mobileNumber?.trim() ?: ""
                    "Email" -> userDto?.email?.trim() ?: ""
                    "Password" -> userDto?.password ?: ""
                    else -> ""
                }
            )
        }
    }

    val defaultSelectedOption = when {
        currentUser.isAdmin -> "Admin"
        else -> "Farmers"
    }
    var validIdState by remember { mutableStateOf(userDto?.validId) }
    var selectedOption by remember {
        val value = when {
            userDto == null -> defaultSelectedOption
            userDto.isAdmin -> "Admin"
            userDto.isTechnician -> "Technician"
            else -> "Farmers"
        }
        mutableStateOf(value)
    }

    val errors = remember {
        listOfLabel.associateWith { mutableStateOf("") }
    }
    var isButtonEnabled by remember { mutableStateOf(true) }
    val isEnableSubmit = remember { mutableStateOf(true) }
    var radioError by remember { mutableStateOf(false) }

    val farmersViewModel: FarmersViewModel = hiltViewModel()
    val addressList by remember { mutableStateOf(farmersViewModel.fetchAddresses()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Spacer(modifier = Modifier.height(50.dp))
        }
        item {
              Text(text = title,
                  fontFamily = FontFamily.Serif,
                  fontSize = 24.sp,
                  modifier = Modifier
                      .offset(y = (-6).dp)
            )
        }

        item {
            ContainerLabelValue(
                statesValue = statesValue,
                chosenOption = selectedOption,
                onSelect = { option ->
                    selectedOption  = option
                },
                includePassword = true,
                currentUser = currentUser,
                addressList = addressList
            )
        }

        if(currentUser.isTechnician) {
            item {
                UploadId(
                    validId = validIdState,
                    onImageSelected = { uri, base64 ->
                        validIdState = base64
                        Log.d("UserFormUI", "Uploaded valid ID: $validIdState")
                    }
                )
            }
        }

        item {
            ButtonSubmitData(
                statesValue = statesValue,
                targetUserDto = userDto,
                selectedOption = selectedOption,
                onSubmit = { updatedUserDto ->
                    onSubmit(updatedUserDto.copy(validId = validIdState))
                },
                errors = errors,
                isEnableSubmit = isButtonEnabled,
                currentUser = currentUser
            )
        }

        item{
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun UploadId(
    validId: String?,
    onImageSelected: (Uri?, String?) -> Unit
) {
    val context = LocalContext.current
    var selectedImgUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    LaunchedEffect(validId) {
        if (!validId.isNullOrEmpty()) {
            val byteArray = Base64.decode(validId, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            selectedImgUri = saveBitmapToUri(context, bitmap)
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImgUri = uri
                val base64 = encodeUriToBase64(context, uri)
                onImageSelected(uri, base64)
            }
        }
    )

    Box(
        Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(RectangleShape)
                .background(if (selectedImgUri != null) Color.White else Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImgUri != null) {
                AsyncImage(
                    model = selectedImgUri,
                    contentDescription = "Valid ID",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text("No Image Uploaded",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun encodeUriToBase64(context: Context, uri: Uri): String? {
    val byteArray = getBytesFromUri(context, uri)
    return byteArray?.let { Base64.encodeToString(it, Base64.DEFAULT) }
}

fun getBytesFromUri(context: Context, uri: Uri): ByteArray? {
    return context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
}

@Composable
fun ContainerLabelValue(
    statesValue: Map<String, MutableState<String>>,
    chosenOption: String,
    currentUser: UserDto,
    includePassword: Boolean,
    onSelect: (option: String) -> Unit,
    addressList: List<AddressDto>
) {
    val firstNameKey = "First Name"
    val firstName = statesValue[firstNameKey]
    TextFieldContainer(
        textFieldLabel = firstNameKey,
        textFieldValue = firstName?.value ?: "",
        onValueChange = { newValue -> firstName?.value = newValue },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif
        )
    )

    val middleNameKey = "Middle Name"
    val middleName = statesValue[middleNameKey]
    TextFieldContainer(
        textFieldLabel = "Middle Name",
        textFieldValue = middleName?.value ?: "",
        onValueChange = { newValue -> middleName?.value = newValue },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif
        )
    )

    val lastNameKey = "Last Name"
    val lastName = statesValue[lastNameKey]
    TextFieldContainer(
        textFieldLabel = lastNameKey,
        textFieldValue = lastName?.value ?: "",
        onValueChange = { newValue -> lastName?.value = newValue },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif
        )
    )

    val dateOfBirthKey = "Date Of Birth"
    val dateOfBirth = statesValue[dateOfBirthKey]
    DatePickerField(
        label = dateOfBirthKey,
        dateValue = dateOfBirth?.value ?: "",
        onDateChange = { newValue ->
            dateOfBirth?.value = newValue
        }
    )
    if(!currentUser.isTechnician) {
        Spacer(modifier = Modifier.height(5.dp))
        var expanded by remember { mutableStateOf(false) }
        var selectItem by remember { mutableStateOf(statesValue["Address"]?.value ?: "") }
        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

        Row(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Address",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontSize = 17.sp
            )

            Text(text = " : ")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable { expanded = !expanded }

            ) {
                if (selectItem.isEmpty()) {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 10.dp)
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Select Address",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(icon, "Dropdown Icon")

                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth(),
                            color = Color.Black
                        )
                    }
                } else {
                    TextField(
                        value = selectItem,
                        onValueChange = { newValue ->
                            selectItem = newValue
                            statesValue["Address"]?.value = newValue
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            }
                            .clickable { expanded = !expanded },
                        trailingIcon = {
                            Icon(icon, "Dropdown Icon",
                                modifier = Modifier
                                    .clickable { expanded = !expanded }
                            )
                        },
                        readOnly = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif
                        ),
                        colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                        )
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(275.dp)
                        .background(color = Color.White)
                        .heightIn(max = 200.dp)
                        .offset(y = 8.dp)
                ) {
                    addressList.forEach { address ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = address.name,
                                    color = Color.Black,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 16.sp
                                )
                            }, onClick = {
                                selectItem = address.name
                                statesValue["Address"]?.value = address.name
                                expanded = false
                            })
                    }
                }
            }
        }
    }

    val mobileNumberKey = "Mobile Number"
    val mobileNumber = statesValue[mobileNumberKey]
    TextFieldContainer(
        textFieldLabel = mobileNumberKey,
        textFieldValue = mobileNumber?.value ?: "",
        onValueChange = { newValue -> mobileNumber?.value = newValue },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif
        )
    )

    val userNameKey = "Email"
    val userName = statesValue[userNameKey]
    TextFieldContainer(
        textFieldLabel = userNameKey,
        textFieldValue = userName?.value ?: "",
        onValueChange = { newValue -> userName?.value = newValue },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 17.sp,
            fontFamily = FontFamily.SansSerif
        )
    )

    if (includePassword) {
        val passwordKey = "Password"
        val password = statesValue[passwordKey]
        TextFieldContainer(
            textFieldLabel = passwordKey,
            textFieldValue = password?.value ?: "",
            onValueChange = { newValue -> password?.value = newValue },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 17.sp,
                fontFamily = FontFamily.SansSerif
            )
        )
    }

//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .height(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        if (radioErrorMsg.isNotEmpty()) {
//            Text(
//                text = radioErrorMsg,
//                color = Color.Red,
//                fontSize = 11.sp,
//            )
//        }
//    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (currentUser.isAdmin) {
            Row(
                Modifier
                    .selectable(
                        selected = true,
                        onClick = { }
                    )
            ) {
                RadioButton(
                    selected = chosenOption == "Admin",
                    onClick = { onSelect("Admin") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Blue,
                        unselectedColor = Color.Gray
                    ),
                    modifier = Modifier
                        .size(18.dp)
                        .padding(10.dp)
                )
                Text(
                    text = "Admin",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Row(
                    Modifier
                        .selectable(
                            selected = true,
                            onClick = { }
                        )
                ) {
                    RadioButton(
                        selected = chosenOption == "Technician",
                        onClick = { onSelect("Technician") },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Blue,
                            unselectedColor = Color.Gray
                        ),
                        modifier = Modifier
                            .size(18.dp)
                            .padding(10.dp)
                    )
                    Text(
                        text = "Technician",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                    )
                }
            }
        }
        if (currentUser.isTechnician) {
            Row(
                Modifier
                    .selectable(
                        selected = true,
                        onClick = { }
                    )
            ) {
                RadioButton(
                    selected = true,
                    onClick = { onSelect("Farmers") },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Blue,
                        unselectedColor = Color.Gray
                    ),
                    modifier = Modifier
                        .size(18.dp)
                        .padding(10.dp)
                )
                Text(
                    text = "Farmers",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

@Composable
fun TextFieldContainer(
    textFieldLabel: String,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle
) {
    val isPasswordField = textFieldLabel == "Password"
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isPhoneNumberField = textFieldLabel == "Mobile Number"

//    val colors = if (isError) {
//        OutlinedTextFieldDefaults.colors(
//            unfocusedContainerColor = Color.Red,
//        )
//    } else {
//        OutlinedTextFieldDefaults.colors(
//            unfocusedContainerColor = Color.Transparent
//        )
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = textFieldLabel,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(" : ", fontWeight = FontWeight.Bold)
            TextField(
                value = textFieldValue,
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                visualTransformation = if (isPasswordField && !isPasswordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                trailingIcon = {
                    if (isPasswordField) {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPasswordVisible) R.drawable.visibilityon else R.drawable.visibility_off
                                ),
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }
    }
}


@SuppressLint("RememberReturnType")
@Composable
fun DatePickerField(
    label: String, dateValue: String,
    onDateChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val isoFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                isoFormat.timeZone = TimeZone.getTimeZone("UTC")
                val dateISO = isoFormat.format(calendar.time)
                onDateChange(dateISO)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val displayDate = try {
        val date =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateValue)
        date?.let { displayFormat.format(it) } ?: "Select Date"
    } catch (e: Exception) {
        "Select Date"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 4.dp)
            .padding(top = 8.dp)
            .clickable {
                datePickerDialog.show()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.width(18.dp))

            Icon(
                painter = painterResource(id = R.drawable.calendar_icon),
                contentDescription = "Calendar Icon",
                modifier = Modifier.size(24.dp)
            )

            Text(" : ", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                modifier = Modifier
                    .clickable {
                        datePickerDialog.show()
                    }
                    .padding(4.dp)
                    .background(Color.Transparent),
            ) {
                Box(
                    modifier = Modifier
                        .width(202.dp)
                        .height(40.dp)
                        .background(Color.White)
                ) {
//                    if (isError) {
//                        Text(
//                            text = errorMessage,
//                            color = Color.Red,
//                            modifier = Modifier.padding(top = 4.dp),
//                            fontSize = 12.sp
//                        )
//                    }else {
                    Text(
                        text = displayDate,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterStart),
                        fontSize = 17.sp,
                        color = Color.Black
                    )
//                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonSubmitData(
    statesValue: Map<String, MutableState<String>>,
    selectedOption: String,
    targetUserDto: UserDto?,
    onSubmit: (UserDto) -> Unit,
    errors: Map<String, MutableState<String>>,
    isEnableSubmit: Boolean,
    currentUser: UserDto
) {
    Button(
        onClick = {
//            val hasError = validateForm(errors, statesValue)
//            if (!hasError) {
            val address = if (currentUser.isTechnician && selectedOption == "Farmers") {
                currentUser.address ?: ""
            } else {
                statesValue["Address"]?.value ?: ""
            }
                val userDto = UserDto(
                    id =  targetUserDto?.id,
                    firstName = statesValue["First Name"]?.value ?: "",
                    middleName = statesValue["Middle Name"]?.value ?: "",
                    lastName = statesValue["Last Name"]?.value ?: "",
                    address = address,
                    mobileNumber = statesValue["Mobile Number"]?.value ?: "",
                    dateOfBirth = statesValue["Date Of Birth"]?.value ?: "",
                    email = statesValue["Email"]?.value ?: "",
                    password = statesValue["Password"]?.value ?: targetUserDto?.password ?: "",
                    isAdmin = selectedOption == "Admin",
                    isTechnician = selectedOption == "Technician",
                    isFarmers = selectedOption == "Farmers",
                    validId = targetUserDto?.validId
                )

                if (targetUserDto?.id == null) {
                    userDto.password = statesValue["Password"]?.value?.hashPassword() ?: "";
                } else {
                    userDto.password = targetUserDto.password
                }

                onSubmit(userDto)
//            }
        },
        enabled = isEnableSubmit,
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF136204),
            contentColor = Color(0xFFFFFFFF)
        ),
    ) {
        Text("Submit", fontSize = 18.sp)
    }
}
fun validateForm(
    errors: Map<String, MutableState<String>>,
    statesValue: Map<String, MutableState<String>>,
): Boolean {
//    var hasError = false
//    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
//    statesValue.forEach { (label, state) ->
//        when (label) {
//            "First Name", "Last Name" -> {
//                val name = state.value
//                if (name.isBlank()) {
//                    errors[label]?.value = "Cannot be empty"
//                    hasError = true
//                } else if (name.any { it.isDigit() }) {
//                    errors[label]?.value = "Cannot contain numbers"
//                    hasError = true
//                } else if (!name.first().isUpperCase()) {
//                    errors[label]?.value = "First letter uppercase"
//                    hasError = true
//                } else {
//                    errors[label]?.value = ""
//                }
//            }
//
//            "Email" -> {
//                val email = state.value
//                if (!emailPattern.matches(email)) {
//                    errors[label]?.value = "Invalid email address"
//                    hasError = true
//                } else if (email != email.lowercase()) {
//                    errors[label]?.value = "Email must be lowercase"
//                    hasError = true
//                } else {
//                    errors[label]?.value = ""
//                }
//            }
//
//            "Address" -> {
//                val address = state.value
//                if (address.isBlank()) {
//                    errors[label]?.value = "Cannot be empty"
//                    hasError = true
//                } else if (address.length < 5) {
//                    errors[label]?.value = "Address is too short"
//                    hasError = true
//                } else if (!address.matches(Regex("^[a-zA-Z0-9,. ]+$"))) {
//                    errors[label]?.value = "Invalid address"
//                    hasError = true
//                } else {
//                    errors[label]?.value = ""
//                }
//            }
//
//            "Mobile Number" -> {
//                if (state.value.length != 11) {
//                    errors[label]?.value = "Must be 11 digits"
//                    hasError = true
//                } else if (!state.value.startsWith("09")) {
//                    errors[label]?.value = "Must start with '09'"
//                    hasError = true
//                } else {
//                    errors[label]?.value = ""
//                }
//            }
//
//            "Password" -> {
//                if (state.value.length < 6) {
//                    errors[label]?.value = "Must be at least 6 characters"
//                    hasError = true
//                } else if (state.value.contains(" ")) {
//                    errors[label]?.value = "Cannot contain spaces"
//                    hasError = true
//                } else {
//                    errors[label]?.value = ""
//                }
//            }
//
//            "Date Of Birth" -> {
//                if (state.value.isEmpty()) {
//                    errors[label]?.value = "Date of birth is required"
//                    hasError = true
//                } else {
//                    errors[label]?.value = ""
//                }
//            }
//        }
//    }
//    return hasError

    return false
}