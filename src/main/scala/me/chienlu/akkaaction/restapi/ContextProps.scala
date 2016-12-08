package me.chienlu.akkaaction.restapi

import me.chienlu.akkaaction.restapi.compress.CompressContextProps
import me.chienlu.akkaaction.restapi.decompress.DecompressContextProps

/**
 * Context Props
 * Created by Chien Lu.
 */
class ContextProps
  extends CompressContextProps
  with DecompressContextProps {
}
