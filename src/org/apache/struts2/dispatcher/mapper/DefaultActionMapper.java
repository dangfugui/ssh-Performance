package org.apache.struts2.dispatcher.mapper;

import com.htht.commonweb.JavaEEbugRepair;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.util.PrefixTrie;

public class DefaultActionMapper
  implements ActionMapper
{
  protected static final String METHOD_PREFIX = "method:";
  protected static final String ACTION_PREFIX = "action:";
  protected static final String REDIRECT_PREFIX = "redirect:";
  protected static final String REDIRECT_ACTION_PREFIX = "redirect-action:";
  protected boolean allowDynamicMethodCalls = true;

  protected boolean allowSlashesInActionNames = false;

  protected boolean alwaysSelectFullNamespace = false;

  protected PrefixTrie prefixTrie = null;

  protected List<String> extensions = new ArrayList() {  } ;
  protected Container container;

  public DefaultActionMapper() {
    this.prefixTrie = new PrefixTrie()
    {
    };
  }

  protected void addParameterAction(String prefix, ParameterAction parameterAction)
  {
    this.prefixTrie.put(prefix, parameterAction);
  }
  @Inject("struts.enable.DynamicMethodInvocation")
  public void setAllowDynamicMethodCalls(String allow) {
    this.allowDynamicMethodCalls = "true".equals(allow);
  }
  @Inject("struts.enable.SlashesInActionNames")
  public void setSlashesInActionNames(String allow) {
    this.allowSlashesInActionNames = "true".equals(allow);
  }
  @Inject("struts.mapper.alwaysSelectFullNamespace")
  public void setAlwaysSelectFullNamespace(String val) {
    this.alwaysSelectFullNamespace = "true".equals(val);
  }
  @Inject
  public void setContainer(Container container) {
    this.container = container;
  }
  @Inject("struts.action.extension")
  public void setExtensions(String extensions) {
    if ((extensions != null) && (!"".equals(extensions))) {
      List list = new ArrayList();
      String[] tokens = extensions.split(",");
      for (String token : tokens) {
        list.add(token);
      }
      if (extensions.endsWith(",")) {
        list.add("");
      }
      this.extensions = Collections.unmodifiableList(list);
    } else {
      this.extensions = null;
    }
  }

  public ActionMapping getMappingFromActionName(String actionName) {
    ActionMapping mapping = new ActionMapping();
    mapping.setName(actionName);
    return parseActionName(mapping);
  }

  public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager)
  {
    ActionMapping mapping = new ActionMapping();
    String uri = getUri(request);

    int indexOfSemicolon = uri.indexOf(";");
    uri = indexOfSemicolon > -1 ? uri.substring(0, indexOfSemicolon) : uri;

    uri = dropExtension(uri, mapping);
    if (uri == null) {
      return null;
    }

    parseNameAndNamespace(uri, mapping, configManager);

    handleSpecialParameters(request, mapping);

    if (mapping.getName() == null) {
      return null;
    }

    parseActionName(mapping);

    return mapping;
  }

  protected ActionMapping parseActionName(ActionMapping mapping) {
    if (mapping.getName() == null) {
      return mapping;
    }
    if (this.allowDynamicMethodCalls)
    {
      String name = mapping.getName();
      int exclamation = name.lastIndexOf("!");
      if (exclamation != -1) {
        mapping.setName(name.substring(0, exclamation));
        mapping.setMethod(name.substring(exclamation + 1));
      }
    }
    return mapping;
  }

  public void handleSpecialParameters(HttpServletRequest request, ActionMapping mapping)
  {
    Set uniqueParameters = new HashSet();
    Map parameterMap = request.getParameterMap();
    Iterator iterator = parameterMap.keySet().iterator();
    while (iterator.hasNext()) {
      String key = (String)iterator.next();

      if ((key.endsWith(".x")) || (key.endsWith(".y"))) {
        key = key.substring(0, key.length() - 2);
      }
      
      if (JavaEEbugRepair.repair_s2_017(key)) {
          return;
      }

      if (!uniqueParameters.contains(key)) {
        ParameterAction parameterAction = (ParameterAction)this.prefixTrie.get(key);

        if (parameterAction != null) {
          parameterAction.execute(key, mapping);
          uniqueParameters.add(key);
          break;
        }
      }
    }
  }

  protected void parseNameAndNamespace(String uri, ActionMapping mapping, ConfigurationManager configManager)
  {
    int lastSlash = uri.lastIndexOf("/");
    String name;
    String namespace;
    if (lastSlash == -1) {
      namespace = "";
      name = uri;
    }
    else
    {
      if (lastSlash == 0)
      {
        namespace = "/";
        name = uri.substring(lastSlash + 1);
      }
      else
      {
        if (this.alwaysSelectFullNamespace)
        {
          namespace = uri.substring(0, lastSlash);
          name = uri.substring(lastSlash + 1);
        }
        else {
          Configuration config = configManager.getConfiguration();
          String prefix = uri.substring(0, lastSlash);
          namespace = "";
          boolean rootAvailable = false;

          for (Object cfg : config.getPackageConfigs().values()) {
            String ns = ((PackageConfig)cfg).getNamespace();
            if ((ns != null) && (prefix.startsWith(ns)) && ((prefix.length() == ns.length()) || (prefix.charAt(ns.length()) == '/')) && 
              (ns.length() > namespace.length())) {
              namespace = ns;
            }

            if ("/".equals(ns)) {
              rootAvailable = true;
            }
          }

          name = uri.substring(namespace.length() + 1);

          if ((rootAvailable) && ("".equals(namespace)))
            namespace = "/";
        }
      }
    }
    if ((!this.allowSlashesInActionNames) && (name != null)) {
      int pos = name.lastIndexOf('/');
      if ((pos > -1) && (pos < name.length() - 1)) {
        name = name.substring(pos + 1);
      }
    }

    mapping.setNamespace(namespace);
    mapping.setName(name);
  }

  /** @deprecated */
  protected String dropExtension(String name)
  {
    return dropExtension(name, new ActionMapping());
  }

  protected String dropExtension(String name, ActionMapping mapping)
  {
    if (this.extensions == null) {
      return name;
    }
    for (String ext : this.extensions) {
      if ("".equals(ext))
      {
        int index = name.lastIndexOf('.');
        if ((index == -1) || (name.indexOf('/', index) >= 0))
          return name;
      }
      else {
        String extension = "." + ext;
        if (name.endsWith(extension)) {
          name = name.substring(0, name.length() - extension.length());
          mapping.setExtension(ext);
          return name;
        }
      }
    }
    return null;
  }

  protected String getDefaultExtension()
  {
    if (this.extensions == null) {
      return null;
    }
    return (String)this.extensions.get(0);
  }

  protected String getUri(HttpServletRequest request)
  {
    String uri = (String)request.getAttribute("javax.servlet.include.servlet_path");

    if (uri != null) {
      return uri;
    }

    uri = RequestUtils.getServletPath(request);
    if ((uri != null) && (!"".equals(uri))) {
      return uri;
    }

    uri = request.getRequestURI();
    return uri.substring(request.getContextPath().length());
  }

  public String getUriFromActionMapping(ActionMapping mapping)
  {
    StringBuilder uri = new StringBuilder();

    if (mapping.getNamespace() != null) {
      uri.append(mapping.getNamespace());
      if (!"/".equals(mapping.getNamespace())) {
        uri.append("/");
      }
    }
    String name = mapping.getName();
    String params = "";
    if (name.indexOf('?') != -1) {
      params = name.substring(name.indexOf('?'));
      name = name.substring(0, name.indexOf('?'));
    }
    uri.append(name);

    if ((null != mapping.getMethod()) && (!"".equals(mapping.getMethod()))) {
      uri.append("!").append(mapping.getMethod());
    }

    String extension = mapping.getExtension();
    if (extension == null) {
      extension = getDefaultExtension();

      ActionContext context = ActionContext.getContext();
      if (context != null) {
        ActionMapping orig = (ActionMapping)context.get("struts.actionMapping");
        if (orig != null) {
          extension = orig.getExtension();
        }
      }
    }

    if (extension != null)
    {
      if ((extension.length() == 0) || ((extension.length() > 0) && (uri.indexOf('.' + extension) == -1))) {
        if (extension.length() > 0) {
          uri.append(".").append(extension);
        }
        if (params.length() > 0) {
          uri.append(params);
        }
      }
    }

    return uri.toString();
  }

  public boolean isSlashesInActionNames()
  {
    return this.allowSlashesInActionNames;
  }
}