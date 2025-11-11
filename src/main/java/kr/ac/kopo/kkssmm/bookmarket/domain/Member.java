package kr.ac.kopo.kkssmm.bookmarket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Table(name="member")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @Column(name="num")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long num;
    @Column(unique = true)
    private String memberId;

    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setMemberId(memberFormDTO.getMemberId());
        member.setPassword(passwordEncoder.encode(memberFormDTO.getPassword()));
        member.setName(memberFormDTO.getName());
        member.setPhone(memberFormDTO.getPhone());
        member.setEmail(memberFormDTO.getEmail());
        member.setAddress(memberFormDTO.getAddress());
        member.setRole(Role.USER);

        return member;
    }
}
