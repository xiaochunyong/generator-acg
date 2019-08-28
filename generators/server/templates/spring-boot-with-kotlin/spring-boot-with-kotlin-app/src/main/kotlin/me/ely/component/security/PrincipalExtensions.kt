// package me.ely.component.security
//
// import org.springframework.security.cas.authentication.CasAuthenticationToken
// import java.security.Principal
//
// fun Principal?.getStaffId(): Int {
//     if (this == null) {
//         return -1
//     }
//     return ((this as CasAuthenticationToken).principal as StaffUserDetails).id
// }
//
// fun Principal?.getStaffUserDetails(): StaffUserDetails? {
//     if (this != null) {
//         val token = this as CasAuthenticationToken?
//         val p = token!!.principal
//         if (p != null) {
//             return p as StaffUserDetails
//         }
//     }
//
//     return null
// }