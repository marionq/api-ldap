package mx.gob.autofin.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.Base64;
import java.util.List;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import mx.gob.autofin.dto.User;
import mx.gob.autofin.model.CRSyncKeycloakModel;
import mx.gob.autofin.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class UserRepoImpl implements UserRepo {

    @Autowired
    ApiKeycloakClientService apiKeycloakClientService;

    public static final String BASE_DN = "";

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public ResponseEntity<Object> create(User p) {
        Name dn = buildDn(p.getUid());

        try {
            ldapTemplate.bind(dn, null, buildAttributes(p));
            CRSyncKeycloakModel cRSyncKeycloakModel = new CRSyncKeycloakModel();
            List<CRSyncKeycloakModel> listResponse = null;
            cRSyncKeycloakModel = apiKeycloakClientService.syncLdapKeycloak();
            listResponse.add(cRSyncKeycloakModel);
            cRSyncKeycloakModel = apiKeycloakClientService.syncLdapKeycloak();
            listResponse.add(cRSyncKeycloakModel);
            return ResponseHandler.generateResponse("created successfully", HttpStatus.OK, listResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.resolve(Integer.parseInt(e.getMessage().split(" ")[0].toString())), null);
        }

    }

    @Override
    public ResponseEntity<Object> update(User p) {
        Name dn = buildDn(p.getUid());

        try {
            System.out.println("Valor DN: " + dn.getAll());
            ldapTemplate.rebind(dn, null, buildAttributes(p));
            CRSyncKeycloakModel cRSyncKeycloakModel = new CRSyncKeycloakModel();
            List<CRSyncKeycloakModel> listResponse = null;
            cRSyncKeycloakModel = apiKeycloakClientService.syncLdapKeycloak();
            listResponse.add(cRSyncKeycloakModel);
            cRSyncKeycloakModel = apiKeycloakClientService.syncLdapKeycloak();
            listResponse.add(cRSyncKeycloakModel);
            
            apiKeycloakClientService.syncLdapKeycloak();
            return ResponseHandler.generateResponse("updated successfully", HttpStatus.OK, listResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.resolve(Integer.parseInt(e.getMessage().split(" ")[0].toString())), null);
        }
    }

    @Override
    public String remove(String userId) {
        Name dn = buildDn(userId);
        // ldapTemplate.unbind(dn, true); //Remove recursively all entries
        ldapTemplate.unbind(dn);
        return "{\"message\": " + "\"" + userId + " removed successfully\"}";
    }

    private Attributes buildAttributes(User p) {

        byte[] bytesDecodificados = Base64.getDecoder().decode(p.getUserPassword());
        String cadenaDecodificada = new String(bytesDecodificados);

        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("inetOrgPerson");
        ocattr.add("organizationalPerson");
        ocattr.add("top");
        ocattr.add("person");
        Attributes attrs = new BasicAttributes();
        attrs.put(ocattr);
        attrs.put("cn", p.getCn());
        attrs.put("sn", p.getSn());
        attrs.put("displayName", p.getDisplayName());
        attrs.put("facsimileTelephoneNumber", p.getFacsimileTelephoneNumber());
        attrs.put("givenName", p.getGivenName());
        attrs.put("l", p.getL());
        attrs.put("mail", p.getMail());
        attrs.put("o", p.getO());
        attrs.put("postalAddress", p.getPostalAddress());
        attrs.put("postalCode", p.getPostalCode());
        attrs.put("postOfficeBox", p.getPostOfficeBox());
        attrs.put("st", p.getSt());
        attrs.put("street", p.getStreet());
        attrs.put("telephoneNumber", p.getTelephoneNumber());
        attrs.put("uid", p.getUid());
        attrs.put("userPassword", cadenaDecodificada);
        return attrs;
    }

    public Name buildDn(String userId) {
        return LdapNameBuilder.newInstance(BASE_DN).add("cn", userId).build();
    }

    public Name buildBaseDn() {
        return LdapNameBuilder.newInstance(BASE_DN).build();
    }

    @Override
    public List<User> retrieve() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        List<User> people = ldapTemplate.search(query().where("objectclass").is("person"), new PersonAttributeMapper());
        return people;
    }

    @Override
    public List<User> findByUid(String uid) {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        List<User> people = ldapTemplate.search(query().where("objectclass").is("person").and("uid").is(uid), new PersonAttributeMapper());
        return people;
    }

    private class PersonAttributeMapper implements AttributesMapper<User> {

        @Override
        public User mapFromAttributes(Attributes attributes) throws NamingException {
            User user = new User();
            user.setCn(null != attributes.get("cn") ? attributes.get("cn").get().toString() : null);
            user.setSn(null != attributes.get("sn") ? attributes.get("sn").get().toString() : null);
            user.setDisplayName(null != attributes.get("displayName") ? attributes.get("displayName").get().toString() : null);
            user.setFacsimileTelephoneNumber(null != attributes.get("facsimileTelephoneNumber") ? attributes.get("facsimileTelephoneNumber").get().toString() : null);
            user.setGivenName(null != attributes.get("givenName") ? attributes.get("givenName").get().toString() : null);
            user.setL(null != attributes.get("l") ? attributes.get("l").get().toString() : null);
            user.setMail(null != attributes.get("mail") ? attributes.get("mail").get().toString() : null);
            user.setO(null != attributes.get("o") ? attributes.get("o").get().toString() : null);
            user.setPostalAddress(null != attributes.get("postalAddress") ? attributes.get("postalAddress").get().toString() : null);
            user.setPostalCode(null != attributes.get("postalCode") ? attributes.get("postalCode").get().toString() : null);
            user.setPostOfficeBox(null != attributes.get("postOfficeBox") ? attributes.get("postOfficeBox").get().toString() : null);
            user.setSt(null != attributes.get("st") ? attributes.get("st").get().toString() : null);
            user.setStreet(null != attributes.get("street") ? attributes.get("street").get().toString() : null);
            user.setTelephoneNumber(null != attributes.get("telephoneNumber") ? attributes.get("telephoneNumber").get().toString() : null);
            user.setUid(null != attributes.get("uid") ? attributes.get("uid").get().toString() : null);
            user.setUserPassword(null != attributes.get("userPassword") ? new String((byte[]) attributes.get("userPassword").get()) : null);

            String ldapOutput = user.getUserPassword();
            user.setUserPassword(Base64.getEncoder().encodeToString(ldapOutput.getBytes()));

            return user;
        }
    }
}
