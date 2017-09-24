package binders

import play.api.i18n.Lang
import play.api.mvc.QueryStringBindable

object QueryStringBinders {

  implicit object LangQueryStringBindable extends QueryStringBindable[Lang] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Lang]] = {
      Option(Lang.get(params("targetLang").head).toRight(s"Language is not recognized"))
    }

    override def unbind(key: String, value: Lang): String = {
      value.code
    }
  }
}
